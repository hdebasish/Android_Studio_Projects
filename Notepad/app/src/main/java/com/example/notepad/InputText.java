package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.notepad.MainActivity.arrayList;
import static com.example.notepad.MainActivity.database;


public class InputText extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_text);
        editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        final int position=intent.getIntExtra("position",0);
        if (position < arrayList.size()) {

            editText.setText(arrayList.get(position));
        editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                        arrayList.set(position, s.toString());
                    MainActivity.adapter.notifyDataSetChanged();

                }


                @Override
                public void afterTextChanged(Editable s) {
                    String sql="INSERT INTO notes(position,text) VALUES(? , ?)";
                    String delete="DELETE FROM notes WHERE position="+(position)+"";
                    database.execSQL(delete);
                    SQLiteStatement statement = database.compileStatement(sql);
                    statement.bindString(1,String.valueOf(position));
                    statement.bindString(2,arrayList.get(position));
                    statement.execute();

                }
            });



       } else {
            arrayList.add("");
            editText.setText(arrayList.get(arrayList.size() - 1));
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    arrayList.set(arrayList.size()-1, s.toString());
                    MainActivity.adapter.notifyDataSetChanged();
                }


                @Override
                public void afterTextChanged(Editable s) {
                    try {

                        String sql="INSERT INTO notes(position,text) VALUES(? , ?)";
                        String delete="DELETE FROM notes WHERE position="+(arrayList.size()-1)+"";
                        database.execSQL(delete);
                        SQLiteStatement statement = database.compileStatement(sql);
                        statement.bindString(1,String.valueOf(arrayList.size()-1));
                        statement.bindString(2,arrayList.get(arrayList.size()-1));
                        statement.execute();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            });

        }



    }


}
