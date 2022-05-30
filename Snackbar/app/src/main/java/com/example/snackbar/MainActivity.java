package com.example.snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View view=findViewById(R.id.coordinator);
        floatingActionButton=findViewById(R.id.fab);
        //Snackbar.make(view,"Hello Snackbar!",Snackbar.LENGTH_LONG).show();
        //snackbar.dismiss();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar= Snackbar.make(view,"Message Sent",Snackbar.LENGTH_INDEFINITE);
                snackbar.setDuration(5000);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                View view1 = snackbar.getView();
                view1.setBackgroundColor(getResources().getColor(R.color.colorSnackbarBackground));
                TextView txt1 = view1.findViewById(com.google.android.material.R.id.snackbar_text);
                txt1.setTextColor(getResources().getColor(R.color.colorSnackbarText));
                TextView txt2 = view1.findViewById(com.google.android.material.R.id.snackbar_action);
                txt2.setTextColor(getResources().getColor(R.color.colorSnackbarActionText));
                snackbar.show();
            }
        });
    }
}