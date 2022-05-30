package com.example.playvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private Button pauseButton;
    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceView);
        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
        skipButton = findViewById(R.id.skip_button);

        mediaPlayer = MediaPlayer.create(this,R.raw.video);
        surfaceView.setKeepScreenOn(true);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(mediaPlayer.getDuration()/4);
            }
        });

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.pause();
            mediaPlayer.release();

        }
    }
}