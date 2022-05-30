package com.example.uber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    Button button;
public void rider(){
    Intent intent = new Intent(getApplicationContext(),RiderActivity.class);
    startActivity(intent);
}

    public void driver(){
        Intent intent = new Intent(getApplicationContext(),Request.class);
        startActivity(intent);
    }


    public void login(View view){
        Switch aSwitch = findViewById(R.id.switch1);
        String userType="Rider";
        if(aSwitch.isChecked()){
            userType="Driver";
        }
        ParseUser.getCurrentUser().put("riderOrDriver",userType);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    if (ParseUser.getCurrentUser().get("riderOrDriver").equals("Rider")){
                        rider();
                    }else if(ParseUser.getCurrentUser().get("riderOrDriver").equals("Driver")) {
                        driver();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        Log.i("Redirecting as ",userType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button  = findViewById(R.id.button);
        if(ParseUser.getCurrentUser()==null){
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e==null){
                        Log.i("Login","Successful");
                    }else {
                        Log.i("Login","Failed"+e.toString());
                    }
                }
            });
        }else {
            if (ParseUser.getCurrentUser().get("riderOrDriver")!=null){
                Log.i("Redirecting as ",ParseUser.getCurrentUser().get("riderOrDriver").toString());
                if (ParseUser.getCurrentUser().get("riderOrDriver").equals("Rider")){
                    rider();
                }else if(ParseUser.getCurrentUser().get("riderOrDriver").equals("Driver")) {
                    driver();
                }
            }
            }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
