package com.shivam.apitquizzer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shivam.apitquizzer.Model.catModel;
import com.shivam.apitquizzer.R;

import com.shivam.apitquizzer.sets;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class catAdapter extends RecyclerView.Adapter<catAdapter.viewHolder> {
    private ArrayList<catModel> list;
    private Context context;

    public catAdapter(ArrayList<catModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override

    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.catitem,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        catModel model = list.get(position);
//        holder.imageView.setImageResource(model.getPic());

//        holder.textView.setText(model.getName());
        holder.setdata(model.getUrl(),model.getName(),model.getSets());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.tv1);
        }
        private void setdata(String url, final String  textView, final int sets)
        {
            Picasso.get().load(url).placeholder(R.color.colorPrimaryDark).into(imageView);
            this.textView.setText(textView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, sets.class);
//                     putExtra() is used to  adds extended data to the intent.
//                     And 2nd activity Intent object can be retrieved via the getIntent()method .
                    intent.putExtra("title",textView);
                    intent.putExtra("sets",sets);
                    itemView.getContext().startActivity(intent);


                }
            });
        }
    }
}
