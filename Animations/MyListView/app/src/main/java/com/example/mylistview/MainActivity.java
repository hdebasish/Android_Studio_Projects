package com.example.mylistview;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        final ArrayList<Integer> friends = new ArrayList<Integer>();

        friends.add(1);
        friends.add(2);
        friends.add(3);
        friends.add(4);
        friends.add(5);
        friends.add(6);
        friends.add(7);
        friends.add(8);

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1, friends);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer clickedItem = friends.get(position);
                Toast.makeText(MainActivity.this,clickedItem,Toast.LENGTH_LONG).show();
            }
        });



    }
}
