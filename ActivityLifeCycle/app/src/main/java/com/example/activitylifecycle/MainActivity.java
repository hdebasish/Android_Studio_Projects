package com.example.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Called"," onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Called"," onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Called"," onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Called"," onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Called"," onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Called"," onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Called"," onRestart()");
    }
}