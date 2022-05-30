package com.example.textfield;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    TextInputEditText usernameEditText;
    TextInputLayout usernameLayout;
    TextInputEditText passwordEditText;
    TextInputLayout passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout=findViewById(R.id.relative_layout);
        //relativeLayout.setOnClickListener(null);
        usernameEditText=findViewById(R.id.username_textInputEditText);
        usernameLayout=findViewById(R.id.username_textInputLayout);
        passwordEditText=findViewById(R.id.password_textInputEditText);
        passwordLayout=findViewById(R.id.password_textInputLayout);


        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(usernameEditText.getText().toString().isEmpty()){

                        usernameLayout.setErrorEnabled(true);
                        usernameLayout.setError("Please enter your username");

                    }else {
                        usernameLayout.setErrorEnabled(false);
                    }
                }else {
                    usernameLayout.setErrorEnabled(false);
                }

            }
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(usernameEditText.getText().toString().isEmpty()){

                    usernameLayout.setErrorEnabled(true);
                    usernameLayout.setError("Please enter your username");

                }else {
                    usernameLayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if(passwordEditText.getText().toString().isEmpty()){

                        passwordLayout.setErrorEnabled(true);
                        passwordLayout.setError("Please enter your username");

                    }else {
                        passwordLayout.setErrorEnabled(false);
                    }
                }else {
                    passwordLayout.setErrorEnabled(false);
                }

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(passwordEditText.getText().toString().isEmpty()){

                    passwordLayout.setErrorEnabled(true);
                    passwordLayout.setError("Please enter your password");

                }else {
                    passwordLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordLayout.setCounterEnabled(true);
        passwordLayout.setCounterMaxLength(8);


        relativeLayout.setOnClickListener(null);
    }
}