package com.example.playfxsoundpool;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    SoundPool soundPool;
    int sound1,sound2,sound3,sound4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOne=findViewById(R.id.buttonOne);
        buttonTwo=findViewById(R.id.buttonTwo);
        buttonThree=findViewById(R.id.buttonThree);
        buttonFour=findViewById(R.id.buttonFour);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);



        AudioAttributes audioAttributes = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        sound1=soundPool.load(this,R.raw.complete,1);
        sound2=soundPool.load(this,R.raw.correct,1);
        sound3=soundPool.load(this,R.raw.defeat_one,1);
        sound4=soundPool.load(this,R.raw.defeat_two,1);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.buttonOne:
                soundPool.play(sound1,1,1,0,0,1);
                break;
            case R.id.buttonTwo:
                soundPool.play(sound2,1,1,0,0,1);
                break;
            case R.id.buttonThree:
                soundPool.play(sound3,1,1,0,0,1);
                break;
            case R.id.buttonFour:
                soundPool.play(sound4,1,1,0,0,1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }

    }
}