package com.example.guess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText editText1;
    Button button;
    Random random = new Random();
    int rand = random.nextInt(20);

    public void buttonPressed(View view)
    {
        editText1 = findViewById(R.id.editText);
        int number = Integer.parseInt(editText1.getText().toString());

        if (number==rand)
        {
            Toast.makeText(getApplicationContext(), "Congratulation you have guessed the correct number!" , Toast.LENGTH_LONG).show();
            rand = random.nextInt(20);
        }
        else if(number<rand)
        {
            Toast.makeText(getApplicationContext(), "Higher!" , Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Lower!" , Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
