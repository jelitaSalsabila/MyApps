package com.example.myapps;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class recycleHolder extends RecyclerView.ViewHolder {
    public TextView articlesName;
    public ImageView imgholder;
    public TextView btndetail;

    public recycleHolder(@NonNull View itemView) {
        super(itemView);

        articlesName = (TextView)itemView.findViewById(R.id.articlesName);
        imgholder = (ImageView) itemView.findViewById(R.id.imgholder);
        btndetail = (Button) itemView.findViewById(R.id.btndetail);
    }
}
