package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    CountDownTimer count;
    int flag=1;
    long interval = 1000;
    int[] minutes = new int[1];
    int[] seconds = new int[1];
    TextView counter;
    Button button ;
    int trackProgress;
    MediaPlayer mediaPlayer;

    public void resetTimmer()
    {
        button.setText("GO");
        flag=1;
        count.cancel();
        seekBar.setEnabled(true);
        seekBar.setProgress(trackProgress+100);

    }

    public void startCount(View view)
    {
        button = findViewById(R.id.button);
        if(flag==1) {
            trackProgress=seekBar.getProgress();
            button.setText("STOP");
            seekBar.setEnabled(false);
            counter = findViewById(R.id.counttime);
             count = new  CountDownTimer(trackProgress+100, interval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    minutes[0] = (int) ((millisUntilFinished / interval) / 60);
                    seconds[0] = (int) ((millisUntilFinished / interval) % 60);
                    counter.setText(minutes[0] + ":" + seconds[0]);
                    if (seconds[0] == 0) {
                        counter.setText(minutes[0] + ":" + seconds[0]);
                        minutes[0] = minutes[0] - 1;
                    }
                    trackProgress=(int) millisUntilFinished;
                }
                @Override
                public void onFinish() {
                    mediaPlayer.start();
                    resetTimmer();
                }
            }.start();
            flag=0;
        }
        else {
            resetTimmer();
            }
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter= findViewById(R.id.counttime);
        counter.setText("0"+":"+"30");
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600000);
        seekBar.setProgress(30000);
        mediaPlayer=MediaPlayer.create(this, R.raw.horn);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 30000;
                minutes[0] = (int) ((progress/interval)/60);
                seconds[0] = (int) ((progress/interval)%60);

                counter.setText(minutes[0]+":"+seconds[0]);

                if (seconds[0]==0) {
                    counter.setText(minutes[0]+":"+seconds[0]);
                    minutes[0]=minutes[0]-1;
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });



    }

}
