package com.example.parsestarterproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    AppCompatEditText usernameSignUpEditText;
    AppCompatEditText emailSignUpEditText;
    AppCompatEditText passwordSignUpEditText;
    AppCompatButton  signUpButton;
    Toolbar toolbar;
    TextView signUpTextView;
    int viewLayout = 0;
    String username = "";
    String email = "";
    String password = "";
    View  view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        usernameEditText=findViewById(R.id.usernameEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        loginButton=findViewById(R.id.loginButton);
        signUpTextView=findViewById(R.id.textViewSignUp);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()){
                    ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user!=null){
                                showAlert("Login Successful", "Welcome, " + username + "!", false);
                            }else {
                                ParseUser.logOut();
                                showAlert("Login Fail", e.getMessage() + " Please try again", true);
                            }
                        }
                    });
                }

            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpActivity();
            }
        });


     /*   signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()){
                    ParseUser user = new ParseUser();
                    user.setUsername(usernameEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null){
                                ParseUser.logOut();
                                showAlert("Sign up successful","Please verify your email address before you login",false);
                            }else {
                                ParseUser.logOut();
                                showAlert("Cannot create your account",e.getMessage(),false);
                            }
                        }
                    });
                }


            }
        });*/

    }

    private void showAlert(String title,String message,Boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (!error){
                            startUserActivity();
                        }
                    }
                }).show();
    }

    private void startUserActivity() {
        Intent intent = new Intent(MainActivity.this,UserActivity.class);
        startActivity(intent);
    }


    private void signUpActivity() {
        view = getLayoutInflater().inflate(R.layout.activity_signup,null);
        usernameSignUpEditText=view.findViewById(R.id.editTextTextPersonName);
        emailSignUpEditText=view.findViewById(R.id.editTextTextEmailAddress);
        passwordSignUpEditText=view.findViewById(R.id.editTextTextPassword);
        signUpButton=view.findViewById(R.id.buttonSignUp);
        
        setContentView(view);
        viewLayout=1;
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameSignUpEditText!=null && emailSignUpEditText!=null && passwordSignUpEditText!=null){
                    username=usernameSignUpEditText.getText().toString();
                    email=emailSignUpEditText.getText().toString();
                    password=passwordSignUpEditText.getText().toString();
                    signUpUser(username,email,password);
                }
            }
        });
        
        

    }

    private void signUpUser(String username, String email, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    ParseUser.logOut();
                    showAlert("Sign up successful","Please verify your email address before you login",true);
                }else {
                    ParseUser.logOut();
                    showAlert("Cannot create your account",e.getMessage(),true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewLayout==1){
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            viewLayout=0;
        }else {
            super.onBackPressed();
        }
    }
}