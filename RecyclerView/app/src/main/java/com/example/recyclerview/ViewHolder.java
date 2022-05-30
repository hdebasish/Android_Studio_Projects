package com.example.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name;
    public TextView desc;
    public LinearLayout recyclerItem;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        image=itemView.findViewById(R.id.imageView);
        name=itemView.findViewById(R.id.name);
        desc=itemView.findViewById(R.id.desc);
        recyclerItem=itemView.findViewById(R.id.recyclerItem);
    }
}
