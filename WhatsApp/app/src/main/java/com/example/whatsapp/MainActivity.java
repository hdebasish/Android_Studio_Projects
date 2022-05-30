package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText cnfPassword;
    Button logIn;
    Button signUp;
    TextView info1;
    TextView info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);
        cnfPassword=findViewById(R.id.editTextCnfPassword);
        logIn=findViewById(R.id.buttonLogin);
        signUp=findViewById(R.id.buttonSignup);
        info1=findViewById(R.id.textView);
        info2=findViewById(R.id.textView2);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        if(ParseUser.getCurrentUser()!=null) {
            Intent intent = new Intent(getApplicationContext(),UserList.class);
            startActivity(intent);
        }

    }

    public void textClick(View view){

        if (info2.getText().equals("Sign Up")){
            Log.i("Status","Done!");
            logIn.setVisibility(View.INVISIBLE);
            cnfPassword.setVisibility(View.VISIBLE);
            signUp.setVisibility(View.VISIBLE);
            info1.setText("Already have an account?");
            info2.setText("Log In");
        }
        else if (info2.getText().equals("Log In")){
            Log.i("Status","Done!");
            logIn.setVisibility(View.VISIBLE);
            cnfPassword.setVisibility(View.INVISIBLE);
            signUp.setVisibility(View.INVISIBLE);
            info1.setText("Don't have an account?");
            info2.setText("Sign Up");
        }

    }

    public void signUp(View view){
        if (password.getText().toString().equals(cnfPassword.getText().toString())){
            Log.i("Response","Yes");
            ParseUser user = new ParseUser();
            user.setUsername(username.getText().toString());
            user.setPassword(cnfPassword.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){
                        Intent intent = new Intent(getApplicationContext(),UserList.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void logIn(View view){
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e==null){
                    Intent intent = new Intent(getApplicationContext(),UserList.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}