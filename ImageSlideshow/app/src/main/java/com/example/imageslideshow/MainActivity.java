package com.example.imageslideshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    SlideShowAdapter adapter;
    CircleIndicator indicator;
    Handler handler;
    Runnable runnable;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        viewPager=findViewById(R.id.viewPager);
        indicator=findViewById(R.id.circularIndicator);
        setSupportActionBar(toolbar);
        adapter = new SlideShowAdapter(this);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                int i = viewPager.getCurrentItem();
                if (i==adapter.images.length-1){
                    i=0;
                    viewPager.setCurrentItem(i,true);
                }else {
                    i++;
                    viewPager.setCurrentItem(i,true);
                }
            }
        };

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },4000,4000);


    }
}