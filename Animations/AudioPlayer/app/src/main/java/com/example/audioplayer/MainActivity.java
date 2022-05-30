package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.media.AudioManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    AudioManager audioManager;

    public void start(View view)
    {
        mPlayer.start();
    }

    public void pause(View view)
    {
        mPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlayer= MediaPlayer.create(this,R.raw.love);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolumn =audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolumn =audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volumnControl = (SeekBar)findViewById(R.id.volume);
        volumnControl.setMax(maxVolumn);
        volumnControl.setProgress(curVolumn);

        volumnControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Progress", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }
        });

        final SeekBar scrubAudio = (SeekBar)findViewById(R.id.scrub);

        scrubAudio.setMax(mPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                scrubAudio.setProgress(mPlayer.getCurrentPosition());

            }
        },0,100);

        scrubAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mPlayer.seekTo(progress);

                }

        });


    }
}
