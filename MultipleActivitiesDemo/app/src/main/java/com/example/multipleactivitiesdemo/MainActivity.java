package com.example.multipleactivitiesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        arrayList.add("India");
        arrayList.add("USA");
        arrayList.add("Russia");
        arrayList.add("Japan");
        arrayList.add("Germany");
        arrayList.add("United Kingdom");
        final Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Selected ", arrayList.get(position));
                intent.putExtra("Country",arrayList.get(position));
                startActivity(intent);

            }
        });
    }
}
