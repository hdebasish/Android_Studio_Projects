package com.example.loginpage;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void clickFunction_button(View view)
    {
        EditText editText1 = (EditText) findViewById(R.id.editText1);

        EditText editText2 = (EditText) findViewById(R.id.editText2);

        Log.i("Usename", editText1.getText().toString());

        Log.i("Password", editText2.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
