package com.example.smiledetector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smiledetector.R;
import com.example.smiledetector.model.FaceDetectionModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FaceDetectionAdapter extends RecyclerView.Adapter<FaceDetectionAdapter.ViewHolder> {

    private final List<FaceDetectionModel> faceDetectionModelList;
    private final Context context;

    public FaceDetectionAdapter(List<FaceDetectionModel> faceDetectionModelList, Context context) {
        this.faceDetectionModelList = faceDetectionModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public FaceDetectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_face_detection,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaceDetectionAdapter.ViewHolder holder, int position) {
        FaceDetectionModel faceDetectionModel = faceDetectionModelList.get(position);
        holder.text1.setText(String.format("Face %s", String.valueOf(faceDetectionModel.getId())));
        holder.text2.setText(faceDetectionModel.getText());

    }

    @Override
    public int getItemCount() {
        return faceDetectionModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text1;
        public TextView text2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.item_face_detection_textView1);
            text2=itemView.findViewById(R.id.item_face_detection_textView2);

        }
    }
}
