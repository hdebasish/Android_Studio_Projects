package com.example.hideui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    int flag = 1;

    public void display(View view)
    {
        Button button =findViewById(R.id.show);
        if(flag==1)
        {
            textView.setVisibility(View.INVISIBLE);
            flag=0;
            button.setText("SHOW");
        }
        else {
            textView.setVisibility(View.VISIBLE);
            flag=1;
            button.setText("HIDE");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }
}
