package com.example.visionboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.visionboard.model.Board;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    BoardListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState==null){
            FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
            listFragment = new BoardListFragment();
            fragmentTransaction.add(R.id.mainActivity_frame,listFragment,TAG_LIST_FRAGMENT);
            fragmentTransaction.commit();
        }else{
            listFragment= (BoardListFragment) fragmentManager.findFragmentByTag(TAG_LIST_FRAGMENT);
        }


    }
}