package com.example.transientstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.transientstate.model.CounterViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String COUNTER_STATE_KEY = "counter" ;
    Button button;
    TextView textView;
    CounterViewModel viewModelCounter;
   /* int counter = 0;

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(String.valueOf(savedInstanceState.getInt(COUNTER_STATE_KEY)));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(COUNTER_STATE_KEY,counter);
        super.onSaveInstanceState(outState);
    } */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  if (savedInstanceState!=null){
            counter = savedInstanceState.getInt(COUNTER_STATE_KEY);
        } */

        viewModelCounter = new ViewModelProvider(this).get(CounterViewModel.class);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        displayCounter(viewModelCounter.getCounter());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                incrementCounter(v);

            }
        });
    }

    private void incrementCounter(View v) {
        viewModelCounter.setCounter(viewModelCounter.getCounter()+1);
        displayCounter(viewModelCounter.getCounter());
    }

    private void displayCounter(int counter) {
        textView.setText(String.valueOf(viewModelCounter.getCounter()));
    }

}