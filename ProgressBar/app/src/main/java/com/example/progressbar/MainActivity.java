package com.example.progressbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar1,progressBar2;
    Handler handler1,handler2;
    Runnable runnable1,runnable2;
    Timer timer1,timer2;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar1=findViewById(R.id.progressBarCircular);
        progressBar1.setVisibility(View.INVISIBLE);

        progressBar2=findViewById(R.id.progressBarHorizontal);
        progressBar2.setProgress(0);
        progressBar2.setSecondaryProgress(0);
        progressBar2.setMax(100);

        handler1 = new Handler();

        runnable1 = new Runnable() {
            @Override
            public void run() {


                if (progressBar1.getVisibility()==View.INVISIBLE){
                    progressBar1.setVisibility(View.VISIBLE);
                }else {
                    progressBar1.setVisibility(View.INVISIBLE);
                }
            }
        };


        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {

                handler1.post(runnable1);

            }
        },10000,5000);



        handler2 = new Handler();

        runnable2 = new Runnable() {
            @Override
            public void run() {
                if(++i<=100){
                    progressBar2.setProgress(i);
                    progressBar2.setSecondaryProgress(i+10);
                }else {
                    timer2.cancel();
                }

            }
        };


        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {

                handler2.post(runnable2);

            }
        },1000,300);


    }


}