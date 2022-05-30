package com.example.playmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.IDN;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button button;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer();
        button=findViewById(R.id.play_button);
        seekBar=findViewById(R.id.seekBar);
        //  mediaPlayer = MediaPlayer.create(this,R.raw.music);
        try {
            mediaPlayer.setDataSource("https://buildappswithpaulo.com/music/watch_me.mp3");
            mediaPlayer.prepareAsync();
            MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    button.setOnClickListener(view -> {
                        if (mediaPlayer.isPlaying()){
                            pauseMediaPlayer();
                            button.setText(R.string.play_text);
                        }else {
                            playMediaPlayer();
                            button.setText(R.string.pause_text);
                        }
                    });

                }
            };
            mediaPlayer.setOnPreparedListener(onPreparedListener);

            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                int duration = mediaPlayer.getDuration();
                Toast.makeText(this, String.valueOf(duration), Toast.LENGTH_SHORT).show();
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (b){
                        mediaPlayer.seekTo(i);

                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void pauseMediaPlayer() {
        if (mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }
    private void playMediaPlayer() {
        if (mediaPlayer!=null){
            mediaPlayer.start();
        }
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