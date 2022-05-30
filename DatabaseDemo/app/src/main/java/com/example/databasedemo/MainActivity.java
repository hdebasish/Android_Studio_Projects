package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Events",MODE_PRIVATE,null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS historcalEvents (event VARCHAR,year INT(4))");
            myDatabase.execSQL("INSERT INTO historcalEvents (event,year) VALUES('Independence Day',1947)");
            myDatabase.execSQL("INSERT INTO historcalEvents (event,year) VALUES('Republic Day',1950)");
            Cursor c = myDatabase.rawQuery("SELECT * FROM historcalEvents",null);
            int evenIndex=c.getColumnIndex("event");
            int yearIndex=c.getColumnIndex("year");
            c.moveToFirst();
            while (c!=null){
                Log.i("Event",c.getString(evenIndex));
                Log.i("Year",String.valueOf(c.getInt(yearIndex)));
                c.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        try {
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users",MODE_PRIVATE,null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR, age INT(3))");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES('Debasish',24)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES('Rita',54)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES('Debotosh',55)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES('Jesus',33)");
            Cursor c=myDatabase.rawQuery("SELECT * FROM users",null);
            int nameIndex=c.getColumnIndex("name");
            int ageIndex=c.getColumnIndex("age");
            c.moveToFirst();
            while (c!=null){
                Log.i("Name",c.getString(nameIndex));
                Log.i("Age",String.valueOf(c.getInt(ageIndex)));
                c.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        }

}
