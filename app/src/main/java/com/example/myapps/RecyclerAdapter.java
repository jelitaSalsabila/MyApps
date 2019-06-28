package com.example.myapps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecycleHolder> {
    private List<ArticlesModel> list ;
    Context context;

    public RecyclerAdapter(Context context, List<ArticlesModel> list){
        this.context = context;
        this.list = list;
    }
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        RecycleHolder rcv = new RecycleHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(final RecycleHolder holder, final int position) {
        final ArticlesModel articles = list.get(position);
        Glide.with(context)
                .load(articles.getGambar())
                .into(holder.imgholder);
        holder.articlesName.setText(list.get(position).getTitle());
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String url = list.get(position).getUrl();
                Intent i = new Intent(context.getApplicationContext(), DetailScreen.class);
                i.putExtra("url", url);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
