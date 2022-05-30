package com.example.visionboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class AddBoardActivity extends AppCompatActivity {
    public static final String TAG = "ADD_FRAGMENT";

    AddBoardFragment addBoardFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState==null){
            addBoardFragment = new AddBoardFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.add_board_frame,addBoardFragment,TAG);
            fragmentTransaction.commit();
        }else {
            addBoardFragment = (AddBoardFragment) fragmentManager.findFragmentByTag(TAG);
        }
    }
}