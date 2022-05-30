package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoppinglist.adapter.RecyclerViewAdapter;
import com.example.shoppinglist.data.DatabaseHandler;
import com.example.shoppinglist.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fab;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText itemNameEditText;
    EditText itemBrandEditText;
    EditText itemQualityEditText;
    EditText itemQuantityEditText;
    Button saveButton;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        fab=findViewById(R.id.fab);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        itemList = databaseHandler.getAllItems();
        adapter = new RecyclerViewAdapter(itemList,this);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddItemPopup();
            }
        });



    }

    private void addItem() {
        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        Item item = new Item();
        item.setItemName(String.valueOf(itemNameEditText.getText()).trim());
        item.setItemBrand(String.valueOf(itemBrandEditText.getText()).trim());
        item.setItemQuality(String.valueOf(itemQualityEditText.getText()).trim());
        item.setItemQuantity(String.valueOf(itemQuantityEditText.getText()));
        databaseHandler.addItem(item);

    }

    private void openAddItemPopup() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_layout,null);
        itemNameEditText = view.findViewById(R.id.item_name_editText);
        itemBrandEditText = view.findViewById(R.id.item_brand_editText);
        itemQualityEditText = view.findViewById(R.id.item_quality_editText);
        itemQuantityEditText = view.findViewById(R.id.item_quantity_editText);
        saveButton = view.findViewById(R.id.save_button);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!itemNameEditText.getText().toString().isEmpty() && !itemBrandEditText.getText().toString().isEmpty() && !itemQualityEditText.getText().toString().isEmpty() && !itemQuantityEditText.getText().toString().isEmpty()){
                    addItem();
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    dialog.dismiss();
                }else {
                    Toast.makeText(MainActivity.this, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}