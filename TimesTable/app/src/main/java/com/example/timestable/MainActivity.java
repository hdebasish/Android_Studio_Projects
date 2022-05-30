package com.example.timestable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SeekBar seekBar = findViewById(R.id.seekBar);
        final ListView listView = findViewById(R.id.listView);
        final ArrayList<Integer> table = new ArrayList<Integer>(10);
        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1,table);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {



            @Override
            public void onProgressChanged(SeekBar seekBar, int progress , boolean fromUser) {

                arrayAdapter.clear();

                    for (int i = 1; i <= 10; i++) {
                        table.add(progress * i);
                    }
                listView.setAdapter(arrayAdapter);
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
