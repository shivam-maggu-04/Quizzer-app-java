package com.shivam.apitquizzer;

import android.app.Dialog;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivam.apitquizzer.Adapter.catAdapter;
import com.shivam.apitquizzer.Model.catModel;

import java.util.ArrayList;
import java.util.Locale;


public class category extends AppCompatActivity {

    RecyclerView recyclerView;
    private  ArrayList<catModel> list;
    private Dialog loadingdialog;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //        ********** toolbar ***********
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories"); // it set title on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //it show arrow to go back page when it is (true)
//                      ******************

        loadingdialog =new Dialog(this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.corner));
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.setCancelable(false);
//        ***************
//        final: it is used to stop value change,method override,inheritance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
//        ***********

        recyclerView = findViewById(R.id.recycleView);
        list= new ArrayList<>();

        final catAdapter a = new catAdapter(list,this);
        recyclerView.setAdapter(a);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);

//       *********************
//         addListenerForSingleValueEvent : it triggers once and then does not trigger again.
//       getChildren(): Gives access to all of the immediate children of this snapshot.
//       child("Categories") : Get a DataSnapshot for the location at the specified relative path.

        loadingdialog.show();
        myRef.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    list.add(dataSnapshot1.getValue(catModel.class));
                }
                a.notifyDataSetChanged();
                loadingdialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(category.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                loadingdialog.dismiss();
                finish();
            }
        });

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}