package com.example.currencycoverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Double dollar;
    Double rupees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void dollarRupees(View view)
    {

        editText = findViewById(R.id.editText);
        dollar=Double.parseDouble(editText.getText().toString());
        rupees= dollar*71.63;
        Toast.makeText(getApplicationContext(), "₹" + rupees , Toast.LENGTH_SHORT).show();
    }
    public void rupeesDollar(View view)
    {

        editText = findViewById(R.id.editText);
        rupees=Double.parseDouble(editText.getText().toString());
        dollar= rupees*0.014;
        Toast.makeText(getApplicationContext(), "$" + dollar , Toast.LENGTH_SHORT).show();
    }
}
