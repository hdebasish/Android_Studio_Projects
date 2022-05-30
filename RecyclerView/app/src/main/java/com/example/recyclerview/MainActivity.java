package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;

    String[] name = {
            "User1",
            "User2",
            "User3",
            "User4",
            "User5",
            "User6",
            "User7",
            "User8",
            "User9",
            "User10",
            "User11"
    };

    String[] desc = {
            "Description1",
            "Description2",
            "Description3",
            "Description4",
            "Description5",
            "Description6",
            "Description7",
            "Description8",
            "Description9",
            "Description10",
            "Description11",
    };

    int[] image = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,
            R.drawable.img10,
            R.drawable.img11,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recyclerView);
        List<User> sampleUser = new ArrayList<>();

        for(int i=0;i<name.length;i++){
            User user = new User();
            user.username = name[i];
            user.userdesc = desc[i];
            user.userimage = image[i];
            sampleUser.add(user);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        //recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerAdapter(getBaseContext(),sampleUser));


    }
}