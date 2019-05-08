package com.example.myapps;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recycleHolder> {
    private ArrayList<articles> articlesArrayList ;
    Context context;

    public recyclerAdapter(Context context, ArrayList<articles> articlesArrayList){
        this.context = context;
        this.articlesArrayList = articlesArrayList;
    }
    public recycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        recycleHolder rcv = new recycleHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(final recycleHolder holder,final int position) {
        final articles articles = articlesArrayList.get(position);
        Glide.with(context)
                .load(articles.getGambar())
                .into(holder.imgholder);
        holder.articlesName.setText(articlesArrayList.get(position).getTitle());
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String url = articlesArrayList.get(position).getUrl();
                Intent i = new Intent(context.getApplicationContext(), DetailScreen.class);
                i.putExtra("url", url);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }
}
