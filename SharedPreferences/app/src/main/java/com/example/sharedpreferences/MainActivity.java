package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);
        /*
        sharedPreferences.edit().putString("username","Jordan Peterson").apply();
        String username = sharedPreferences.getString("username", "");
        Log.i("Username",username);*/

        ArrayList<String> things = new ArrayList<>();
        things.add("Apple");
        things.add("Ball");
        things.add("Cat");
        things.add("Doll");
        try {
            sharedPreferences.edit().putString("things",ObjectSerializer.serialize(things)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> newThings = new ArrayList<>();
        try {
            newThings= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("things",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("newThings",newThings.toString());

    }
}
