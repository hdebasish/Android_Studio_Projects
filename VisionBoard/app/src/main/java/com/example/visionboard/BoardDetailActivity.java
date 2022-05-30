package com.example.visionboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.visionboard.model.Board;

public class BoardDetailActivity extends AppCompatActivity {

    BoardDetailFragment boardDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        Board board = (Board) getIntent().getSerializableExtra("board");
        if (savedInstanceState==null){
            boardDetailFragment=BoardDetailFragment.newInstance(board);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_detail_container,boardDetailFragment);
            fragmentTransaction.commit();
        }
    }
}