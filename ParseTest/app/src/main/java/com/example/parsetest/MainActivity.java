package com.example.parsetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating a new Object in parse server

     /*   ParseObject score = new ParseObject("Score");

        score.put("userName","Yuvraj");
        score.put("score",150);

        score.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.i("Save In Background","Successful");
                }else {
                    Log.i("Save In Background","Error: "+e.toString());
                }
            }
        });

        //Getting the values from the database of the parse server
        //Modifying the values in the database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Score");
        query.getInBackground("1ACeoFWCoZ", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null && object!=null){
                    object.put("score","108");
                    object.saveInBackground();
                    Log.i("Object Value",object.getString("userName"));
                    Log.i("Object Value", object.getString("score"));
                }
            }
        });


        //Getting specific values from the parse database
        //Modifying the values in the database


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
       // query.whereEqualTo("userName","Yuvraj");
       // query.setLimit(1);

        query.whereGreaterThan("score",100);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    Log.i("Objects retrived",String.valueOf(objects.size()));
                    if (objects.size()>0){
                        for (ParseObject object:objects){
                            Log.i("Player",object.getString("userName"));
                            int score=object.getInt("score");
                            int newScore=score+50;
                            object.put("score",newScore);
                            object.saveInBackground();
                        }
                    }
                }
            }
        });


        //Signing Up user
        ParseUser user = new ParseUser();
        user.setUsername("Debasish");
        user.setPassword("hg21165");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Log.i("Sign In","Successful");
                }else {
                    Log.i("Sign In","Failed");
                }
            }
        });



     //Login User

        ParseUser.logInInBackground("Debasish", "hg21165", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null){
                    Log.i("Login","Successful");
                }
                else {
                    Log.i("Login","Failed"+e.toString());
                }
            }
        });


     //Check if User Logged In

        if (ParseUser.getCurrentUser()!= null){
            Log.i("Current User","User logged in "+ParseUser.getCurrentUser().getUsername());
        }else {
            Log.i("Current User","User not logged In");
        }



     //Logout User

        ParseUser.logOut();

      */

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
