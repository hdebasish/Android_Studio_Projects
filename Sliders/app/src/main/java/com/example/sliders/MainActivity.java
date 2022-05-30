package com.example.sliders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;

public class MainActivity extends AppCompatActivity {

    AppCompatSeekBar seekBar;
    TextView seekBarValue;
    RangeBar rangeBar1;
    TextView rangeBar1Value;
    RangeBar rangeBar2;
    TextView rangeBar2LeftValue;
    TextView rangeBar2RightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar=findViewById(R.id.seekBar);
        seekBarValue=findViewById(R.id.AppCompatSeekBarValue);
        rangeBar1=findViewById(R.id.rangeBar1);
        rangeBar1Value=findViewById(R.id.rangeBar1Value);
        rangeBar2=findViewById(R.id.rangeBar2);
        rangeBar2LeftValue=findViewById(R.id.rangeBar2LeftValue);
        rangeBar2RightValue=findViewById(R.id.rangeBar2RightValue);


        seekBar.setProgress(0);
        seekBar.setKeyProgressIncrement(1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rangeBar1.setSeekPinByIndex(0);

        //rangeBar1.setPinTextColor(getResources().getColor(R.color.colorRangeBarText));
        //rangeBar1.setBarColor(getResources().getColor(R.color.colorAccent));
        //rangeBar1.setTickColors(getResources().getColor(R.color.colorAccent));
        //rangeBar1.setConnectingLineColor(getResources().getColor(R.color.colorAccent));
        //rangeBar1.setPinColor(getResources().getColor(R.color.colorAccent));

        rangeBar1.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                rangeBar1Value.setText(rightPinValue);
            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {

            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {

            }
        });

        rangeBar2.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                rangeBar2LeftValue.setText(leftPinValue);
                rangeBar2RightValue.setText(rightPinValue);
            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {

            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {

            }
        });
    }
}