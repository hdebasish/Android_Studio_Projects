package com.example.parks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.parks.R;
import com.example.parks.model.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ImageSlider> {

    private List<Images> imagesList;

    public ViewPagerAdapter(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_row,parent,false);
        return new ImageSlider(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.ImageSlider holder, int position) {
        Picasso.get()
                .load(imagesList.get(position).getUrl())
                .fit()
                .placeholder(android.R.drawable.stat_sys_download)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ImageSlider extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageSlider(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.viewpager_imageView);
        }
    }
}