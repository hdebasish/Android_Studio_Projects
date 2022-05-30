package com.example.twitterlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.twitter.ParseTwitterUtils;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(view -> {
            ParseTwitterUtils.logIn(MainActivity.this, (user, err) -> {
                if (err != null) {
                    ParseUser.logOut();
                    Log.e("err", "err", err);
                }
                if (user == null) {
                    ParseUser.logOut();
                    Toast.makeText(MainActivity.this, "The user cancelled the Twitter login.", Toast.LENGTH_LONG).show();
                    Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
                } else if (user.isNew()) {
                    Toast.makeText(MainActivity.this, "User signed up and logged in through Twitter.", Toast.LENGTH_LONG).show();
                    Log.d("MyApp", "User signed up and logged in through Twitter!");
                    user.setUsername(ParseTwitterUtils.getTwitter().getScreenName());
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (null == e) {
                                alertDisplayer("First tome login!", "Welcome!");
                            } else {
                                ParseUser.logOut();
                                Toast.makeText(MainActivity.this, "It was not possible to save your username.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "User logged in through Twitter.", Toast.LENGTH_LONG).show();
                    Log.d("MyApp", "User logged in through Twitter!");
                    alertDisplayer("Oh, you!","Welcome back!");
                }
            });
        });
    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(MainActivity.this, LogoutActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
