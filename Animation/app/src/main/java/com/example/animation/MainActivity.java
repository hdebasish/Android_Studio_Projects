package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    public void fade1(View view)
    {
        ImageView bill = findViewById(R.id.imageView1);
        ImageView steve = findViewById(R.id.imageView2);
        bill.animate().alpha(0).setDuration(2000);
        steve.animate().alpha(1).setDuration(2000);
    }
 /*   public void fade2(View view)
    {
        ImageView bill = findViewById(R.id.imageView1);
        ImageView steve = findViewById(R.id.imageView2);
        steve.animate().alpha(0).setDuration(2000);
        bill.animate().alpha(1).setDuration(2000);
    }

*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
