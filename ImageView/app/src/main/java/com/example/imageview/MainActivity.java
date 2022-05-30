package com.example.imageview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewFlipper viewFlipper;
    Button next;
    Button previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == next) {
            viewFlipper.showNext();
        }
        else if (v == previous) {
            viewFlipper.showPrevious();
        }
    }
}
