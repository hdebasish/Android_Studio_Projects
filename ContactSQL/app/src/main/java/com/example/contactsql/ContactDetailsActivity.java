package com.example.contactsql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.contactsql.adapter.RecyclerViewAdapter;

public class ContactDetailsActivity extends AppCompatActivity {

    TextView nameTextView2;
    TextView phoneNumberTextView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        nameTextView2=findViewById(R.id.name_textView2);
        phoneNumberTextView2=findViewById(R.id.phone_number_textView2);

        Intent intent = getIntent();
        String name = intent.getStringExtra(RecyclerViewAdapter.NAME);
        String phoneNumber = intent.getStringExtra(RecyclerViewAdapter.PHONE_NUMBER);
        nameTextView2.setText(name);
        phoneNumberTextView2.setText(phoneNumber);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}