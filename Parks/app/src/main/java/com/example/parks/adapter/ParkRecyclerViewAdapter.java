package com.example.parks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parks.R;
import com.example.parks.model.Park;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ParkRecyclerViewAdapter extends RecyclerView.Adapter<ParkRecyclerViewAdapter.ViewHolder> {

    private final List<Park> parkList;
    private final OnParkClickListener parkClickListener;

    public ParkRecyclerViewAdapter(List<Park> parkList, OnParkClickListener parkClickListener) {
        this.parkList = parkList;
        this.parkClickListener = parkClickListener;
    }

    @NonNull
    @Override
    public ParkRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkRecyclerViewAdapter.ViewHolder holder, int position) {
        Park park = parkList.get(position);
        holder.parkName.setText(park.getName());
        holder.parkType.setText(park.getDesignation());
        holder.parkState.setText(park.getStates());
        if (park.getImages().size()>0){
            Picasso.get()
                    .load(park.getImages().get(0).getUrl())
                    .placeholder(android.R.drawable.stat_sys_download)
                    .error(android.R.drawable.stat_notify_error)
                    .resize(100,100)
                    .centerCrop()
                    .into(holder.parkImageView);

        }




    }

    @Override
    public int getItemCount() {
        return parkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView parkImageView;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;
        OnParkClickListener onParkClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkImageView =itemView.findViewById(R.id.row_park_imageView);
            parkName=itemView.findViewById(R.id.row_park_name_textView);
            parkType=itemView.findViewById(R.id.row_park_type_textView);
            parkState=itemView.findViewById(R.id.row_park_state_textView);
            this.onParkClickListener = parkClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Park currentPark = parkList.get(getAdapterPosition());
            onParkClickListener.onParkClicked(currentPark);
        }
    }
}
