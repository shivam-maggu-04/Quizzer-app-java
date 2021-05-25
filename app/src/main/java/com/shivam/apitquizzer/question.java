package com.shivam.apitquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivam.apitquizzer.Model.catModel;
import com.shivam.apitquizzer.Model.quesModel;

import java.util.ArrayList;
import java.util.List;

public class question extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private Dialog loadingdialog;
    private TextView ques,no;
    private FloatingActionButton book;
    private LinearLayout op;
    private Button share,next;
    private List<quesModel> list;
    int count =0,position =0,score=0;
    private String category;
    private int setNo;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ques = findViewById(R.id.question);
        no = findViewById(R.id.number);
        book = findViewById(R.id.btnbookmark);
        op = findViewById(R.id.option);
        share = findViewById(R.id.btnshare);
        next = findViewById(R.id.btnnext);

        list = new ArrayList<>();

        loadingdialog =new Dialog(this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.corner));
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.setCancelable(false);

        category =getIntent().getStringExtra("category");
        setNo = getIntent().getIntExtra("setNo",1);

        loadingdialog.show();
        myRef.child("SETs").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    list.add(dataSnapshot1.getValue(quesModel.class));
                }
                if(list.size() > 0)
                {
                    for(int i=0;i<4;i++)
                    {
                        op.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View view) {
                                checkAnswer(((Button)view));
                            }
                        });
                    }

                    playanimation(ques,0,list.get(position).getQu());

                    next.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View view) {
                            count=0;
                            next.setEnabled(false);
                            next.setAlpha(0.6f);
                            enbledCheck(true);
                            position++;
                            if(position==list.size())
                            {
//                 score activity
                                Intent scoreIntent=new Intent(question.this,score.class);
                                scoreIntent.putExtra("Score",score);
                                scoreIntent.putExtra("Total",list.size());
                                startActivity(scoreIntent);
                                finish();
                                return;
                            }
//                            count=0;
                            playanimation(ques,0, list.get(position).getQu());
                        }
                    });
                }else {
                    finish();
                    Toast.makeText(question.this, "No Questions", Toast.LENGTH_SHORT).show();
                }
                loadingdialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(question.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                loadingdialog.dismiss();
                finish();
            }
        });
    }

    private void playanimation(final View view, final int value,final String data)
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                String o = "";
                if(value==0 && count < 4)
                {
                    if(count==0)
                    {
                     o = list.get(position).getOptionA();
                    }else if(count==1){
                        o = list.get(position).getOptionB();
                    }else if(count==2){
                        o = list.get(position).getOptionC();
                    }else if(count==3){
                        o = list.get(position).getOptionD();

                    }
                    playanimation(op.getChildAt(count),0,o);
                    count++;
                }
            }
            @Override
            public void onAnimationEnd(Animator animator) {
//               In this we change a data

                if(value==0)
                {
                    try {
                        ((TextView)view).setText(data);
                        no.setText(position+1+"/"+list.size());

                    }catch (ClassCastException ex)
                    {
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playanimation(view,1,data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkAnswer(Button selectOption)
    {
        enbledCheck(false);
        next.setEnabled(true);
        next.setAlpha(1);
        if(selectOption.getText().toString().equals(list.get(position).getCorrect()))
        {
            score++;
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DA66C33E")));
        }
        else
        {
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button cor =(Button)op.findViewWithTag(list.get(position).getCorrect());
            cor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DA66C33E")));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void enbledCheck(boolean en)
    {
        for(int i=0;i<4;i++)
        {
            op.getChildAt(i).setEnabled(en);
            if(en)
            {
                op.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DA655C5B")));
            }
        }
          }
}