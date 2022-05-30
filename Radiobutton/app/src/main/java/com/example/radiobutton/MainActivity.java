package com.example.radiobutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup=findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId==R.id.radioButton1){
                    Toast.makeText(getApplicationContext(),"Radio Button 1 selected",Toast.LENGTH_SHORT).show();
                }else if(checkedId==R.id.radioButton2){
                    Toast.makeText(getApplicationContext(),"Radio Button 2 selected",Toast.LENGTH_SHORT).show();
                }else if(checkedId==R.id.radioButton3){
                    Toast.makeText(getApplicationContext(),"Radio Button 3 selected",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}