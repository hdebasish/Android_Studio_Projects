package com.example.button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Button raisedButton;
    Button flatButton;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        raisedButton=findViewById(R.id.raised_button);
        flatButton=findViewById(R.id.flat_button);
        floatingActionButton=findViewById(R.id.floating_action_button);

        raisedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Raised Button Pressed",Toast.LENGTH_SHORT).show();
            }
        });

        flatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Flat Button Pressed",Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Floating Action Button Pressed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}