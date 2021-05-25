package com.shivam.apitquizzer.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shivam.apitquizzer.R;
import com.shivam.apitquizzer.question;

public class gridAdapter extends BaseAdapter {

    private  int sets =0;
    private  String category ;

    public gridAdapter(int sets,String category)
    {
        this.sets = sets;
        this.category=category;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        View view1;
        if(view==null)
        {
            view1= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setitems,viewGroup,false);
        }
        else {
            view1 = view;

        }

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(viewGroup.getContext(), question.class);
                intent.putExtra("category",category);
                intent.putExtra("setNO",i+1);
                viewGroup.getContext().startActivity(intent);
            }
        });
        ((TextView)view1.findViewById(R.id.textView)).setText(String.valueOf(i+1));
        return view1;
    }
}
