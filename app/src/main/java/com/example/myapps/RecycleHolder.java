package com.example.myapps;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RecycleHolder extends RecyclerView.ViewHolder {
    public TextView articlesName;
    public ImageView imgholder;
    public TextView btndetail;

    public RecycleHolder(@NonNull View itemView) {
        super(itemView);

        articlesName = itemView.findViewById(R.id.articlesName);
        imgholder = itemView.findViewById(R.id.imgholder);
        btndetail =  itemView.findViewById(R.id.btndetail);
    }
}
