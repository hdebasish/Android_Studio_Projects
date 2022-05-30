package com.example.toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void onClickFunction(View view){

        EditText myTextField = (EditText) findViewById(R.id.editText);

        Log.i("info", myTextField.getText().toString());

        Toast.makeText(MainActivity.this, "Hi there, " + myTextField.getText().toString() + "!", Toast.LENGTH_LONG ).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
