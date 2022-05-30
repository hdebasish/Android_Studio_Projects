package com.example.progressdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Handler handler;
    Runnable runnable;
    Timer timer;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Progress Dialog");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        //progressDialog.cancel();

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
                if(++i<=100){
                    progressDialog.setProgress(i);
                }else {
                    progressDialog.cancel();
                    timer.cancel();
                }


            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },8000,100);
    }
}