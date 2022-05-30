package com.example.self.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.self.R;
import com.example.self.model.Journal;
import com.example.self.util.DeleteJournal;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static DeleteJournal deleteJournal;
    private final Context context;
    private final List<Journal> journalList;

    public RecyclerViewAdapter(Context context, List<Journal> journalList, DeleteJournal deleteJournal) {
        this.context = context;
        this.journalList = journalList;
        RecyclerViewAdapter.deleteJournal=deleteJournal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.journal_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Journal journal = journalList.get(position);
        holder.username.setText(journal.getUsername());
        holder.title.setText(journal.getTitle());
        holder.thoughts.setText(journal.getThought());
        Picasso.get()
                .load(journal.getImageUrl())
                .placeholder(android.R.drawable.stat_sys_download)
                .fit()
                .into(holder.imageView);
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(journal.getTimeAdded().getSeconds()*1000);
        holder.dateAdded.setText(timeAgo);

    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView title;
        public TextView thoughts;
        public TextView dateAdded;
        public TextView username;
        public ImageView imageView;
        public ImageButton imageButton;
        public CardView cardView;
        DeleteJournal deleteJournal;



        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            title=itemView.findViewById(R.id.journal_title_list);
            thoughts=itemView.findViewById(R.id.journal_desc_list);
            dateAdded=itemView.findViewById(R.id.journal_timestamp_list);
            imageView=itemView.findViewById(R.id.journal_image_list);
            username=itemView.findViewById(R.id.journal_username_list);
            imageButton=itemView.findViewById(R.id.journal_share_list);
            cardView=itemView.findViewById(R.id.journal_row);
            this.deleteJournal=RecyclerViewAdapter.deleteJournal;

            imageButton.setOnClickListener(this);

            cardView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.journal_share_list){
                if (!TextUtils.isEmpty(title.getText().toString().trim()) && !TextUtils.isEmpty(thoughts.getText().toString().trim()) && imageView.getDrawable()!=null){
                    Bitmap image=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, null,null);
                    Uri imageUri = Uri.parse(path);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    Log.i("path",path);
                    intent.putExtra(Intent.EXTRA_SUBJECT, title.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT, thoughts.getText().toString());
                    intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    intent.setType("image/jpeg");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(Intent.createChooser(intent, "send"));

                }
            }
        }

        @Override
        public boolean onLongClick(View view) {

            if (view.getId()==R.id.journal_row){
                deleteJournal.onLongClick(journalList.get(getAdapterPosition()));
            }
            return true;
        }
    }
}
