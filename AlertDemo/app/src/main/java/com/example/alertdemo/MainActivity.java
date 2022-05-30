package com.example.alertdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SharedPreferences sharedPreferences;;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

         switch(item.getItemId()){
             case R.id.english:
                 String selectedItem=sharedPreferences.getString("english","");
                 textView.setText(selectedItem);
                 sharedPreferences.edit().putString("language",selectedItem).apply();
                 return true;
             case R.id.bengali:
                 selectedItem=sharedPreferences.getString("bengali","");
                 textView.setText(selectedItem);
                 sharedPreferences.edit().putString("language",selectedItem).apply();
                 return true;
             default:
                 return false;

         }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        sharedPreferences = getSharedPreferences("com.example.alertdemo", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("bengali","BENGALI").apply();
        sharedPreferences.edit().putString("english","ENGLISH").apply();
        //sharedPreferences.edit().putString("language","").apply();
        if(sharedPreferences.getString("language","").equals("ENGLISH")) {
            textView.setText(sharedPreferences.getString("english", ""));
        }else if(sharedPreferences.getString("language","").equals("BENGALI")){
            textView.setText(sharedPreferences.getString("bengali", ""));
        }else{
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you Sure?")
                    .setMessage("Do you want to change the language?")
                    .setPositiveButton("ENGLISH", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selectedItem=sharedPreferences.getString("english","");
                            textView.setText(selectedItem);
                            sharedPreferences.edit().putString("language",selectedItem).apply();

                        }
                    })
                    .setNegativeButton("BENGALI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selectedItem=sharedPreferences.getString("bengali","");
                            textView.setText(selectedItem);
                            sharedPreferences.edit().putString("language",selectedItem).apply();
                        }
                    })
                    .show();
        }

        }

    }

