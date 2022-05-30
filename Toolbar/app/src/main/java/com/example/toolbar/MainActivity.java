package com.example.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.item1){
            Toast.makeText(getApplicationContext(),"Item 1 is selected",Toast.LENGTH_SHORT).show();
        }else if (id==R.id.item2){
            Toast.makeText(getApplicationContext(),"Item 2 is selected",Toast.LENGTH_SHORT).show();
        }else if (id==R.id.item3){
            Toast.makeText(getApplicationContext(),"Item 3 is selected",Toast.LENGTH_SHORT).show();
        }else if (id==R.id.search){
            Toast.makeText(getApplicationContext(),"Search is selected",Toast.LENGTH_SHORT).show();
        }else if (id==R.id.cart){
            Toast.makeText(getApplicationContext(),"Cart is selected",Toast.LENGTH_SHORT).show();
        }else if (id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}