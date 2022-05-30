package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText cnfPasswordEditText;
    Button buttonLogIn;
    Button buttonSignUp;
    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        imageView=findViewById(R.id.imageView);
        usernameEditText=findViewById(R.id.usernameEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        cnfPasswordEditText=findViewById(R.id.cnfPasswordEditText);
        buttonLogIn=findViewById(R.id.buttonLogin);
        buttonSignUp=findViewById(R.id.buttonSignup);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);

        if(ParseUser.getCurrentUser()!=null){
            Intent intent = new Intent(getApplicationContext(),YourFeed.class);
            startActivity(intent);
        }
    }

    public void jumpLogin(View view){
        if (textView2.getText().toString().equals("Log In")){
            cnfPasswordEditText.setVisibility(View.INVISIBLE);
            buttonSignUp.setVisibility(View.INVISIBLE);
            buttonLogIn.setVisibility(View.VISIBLE);
            textView1.setText("Don't have an account?");
            textView2.setText("Sign Up");
        }
        else{
            cnfPasswordEditText.setVisibility(View.VISIBLE);
            buttonSignUp.setVisibility(View.VISIBLE);
            buttonLogIn.setVisibility(View.INVISIBLE);
            textView1.setText("Already have an account?");
            textView2.setText("Log In");
        }
    }

    public void signup(View view){
        if(passwordEditText.getText().toString().equals(cnfPasswordEditText.getText().toString())) {
            ParseUser user = new ParseUser();
            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(cnfPasswordEditText.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Intent intent = new Intent(getApplicationContext(), YourFeed.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Password mismatch!", Toast.LENGTH_LONG).show();
        }

    }

    public void login(View view){
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null){
                    Intent intent = new Intent(getApplicationContext(), YourFeed.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
