package com.example.visionboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visionboard.data.ApplicationDatabase;
import com.example.visionboard.model.Board;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddBoardFragment extends Fragment {

    private EditText name;
    private EditText description;
    private Button addImageButton;
    private Button saveBoardButton;
    private ImageView imageView;
    private String imageUrl;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_board,container,false);
        name = view.findViewById(R.id.add_board_name);
        description = view.findViewById(R.id.add_board_description);
        addImageButton = view.findViewById(R.id.add_image);
        saveBoardButton = view.findViewById(R.id.save_board);
        imageView = view.findViewById(R.id.board_image);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveBoardButton.setOnClickListener(v -> {
            saveBoard();
            name.setText("");
            description.setText("");
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
        addImageButton.setOnClickListener(v -> {
            imageUrl = processImage();
            Picasso.get().load(imageUrl).centerCrop().fit().into(imageView);
        });
    }

    private String processImage() {

        int min = 120;
        int max = 290;
        int minId = 50;
        int maxId = 100;

        Random random = new Random();
        int height = min + random.nextInt(max);
        int idRand = minId + random.nextInt(maxId);
        return "https://loremflickr.com/200/"+height+"?lock="+idRand;
    }

    private void saveBoard() {
        String name = this.name.getText().toString().trim();
        String description = this.description.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(description)){
            Board board = new Board(name,description,imageUrl);
            insertBoard(board);
        }else {
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertBoard(Board board) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            ApplicationDatabase database = ApplicationDatabase.getInstance(getContext());
            Long result=database.boardDao().insertBoard(board);
            handler.post(() -> {
            if (result!=null){
                Toast.makeText(getContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
            });
        });

    }


}
