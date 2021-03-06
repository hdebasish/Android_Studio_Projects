package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.memorableplaces.ObjectSerializer.serialize;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> address = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();
        address.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.memorableplaces",Context.MODE_PRIVATE);
        try {
            address= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("address",ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitude",ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitude",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(address.size()>0 && latitudes.size()>0 && longitudes.size()>0)
        {
            if (address.size()==latitudes.size()&& latitudes.size()==longitudes.size()){
                for(int i=0;i<latitudes.size();i++){

                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i))));

                }
            }
        }else {
            address.add("Add a new place...");
            locations.add(new LatLng(0, 0));
        }
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,address);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

    }

}
