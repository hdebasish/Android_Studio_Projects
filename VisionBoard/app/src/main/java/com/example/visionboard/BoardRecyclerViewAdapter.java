package com.example.visionboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visionboard.model.Board;
import com.example.visionboard.util.OnRowClickListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BoardRecyclerViewAdapter extends RecyclerView.Adapter<BoardRecyclerViewAdapter.ViewHolder> {

    private final List<Board> boardList;
    private final OnRowClickListener onRowClickListener;

    public BoardRecyclerViewAdapter(List<Board> boardList, OnRowClickListener onRowClickListener) {
        this.boardList = boardList;
        this.onRowClickListener = onRowClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public BoardRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull BoardRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.board=boardList.get(position);
        holder.boardName.setText(boardList.get(position).getName());
        Picasso.get().load(boardList.get(position).getImageUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View parentView;
        final TextView boardName;
        final ImageView imageView;
        OnRowClickListener listener;

        Board board;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            parentView=itemView;
            boardName=itemView.findViewById(R.id.list_item_board);
            imageView=itemView.findViewById(R.id.board_item_image);
            this.listener=onRowClickListener;
            parentView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION){
                onRowClickListener.OnItemClick(v,position);
            }
        }
    }
}
