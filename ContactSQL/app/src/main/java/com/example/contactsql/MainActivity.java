package com.example.contactsql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.contactsql.adapter.RecyclerViewAdapter;
import com.example.contactsql.data.DatabaseHandler;
import com.example.contactsql.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        contactList = databaseHandler.getAllContacts();

        recyclerViewAdapter = new RecyclerViewAdapter(this,contactList);
        recyclerView.setAdapter(recyclerViewAdapter);









/*

        databaseHandler.addContact(new Contact("Debasish","9733603487"));
        databaseHandler.addContact(new Contact("Rita","9836621728"));
        databaseHandler.addContact(new Contact("Debotosh","7797557839"));
        databaseHandler.addContact(new Contact("Ranjita","8436248752"));
        databaseHandler.addContact(new Contact("Sujit","6148253148"));
        databaseHandler.addContact(new Contact("Rajit","9423345777"));
        databaseHandler.addContact(new Contact("Sumon","7632225488"));
        databaseHandler.addContact(new Contact("Sontu","7458798256"));
        databaseHandler.addContact(new Contact("Ranik","8885633325"));
        databaseHandler.addContact(new Contact("Tomaghana","8854123475"));
        databaseHandler.addContact(new Contact("Indrajit","8754689254"));
        databaseHandler.addContact(new Contact("Subhrajit","7825361478"));
        databaseHandler.addContact(new Contact("Arijit","8854444576"));
        databaseHandler.addContact(new Contact("Pravat","6487958578"));
        databaseHandler.addContact(new Contact("Raktim","7896525478"));
        databaseHandler.addContact(new Contact("Shreya","2354758446"));
        databaseHandler.addContact(new Contact("Avishikta","7778889995"));
        databaseHandler.addContact(new Contact("Torsha","7776665982"));
        databaseHandler.addContact(new Contact("Rupali","2223544875"));
        databaseHandler.addContact(new Contact("Madhurima","2348775624"));

*/
        /*
        COUNT THE NO OF ITEMS IN THE TABLE
         */
       // Log.i("Counts", String.valueOf(databaseHandler.getCount()));

        /*
        ADD items into the table
         */
       /* Contact item1 = new Contact();
        item1.setName("Debasish");
        item1.setPhoneNumber("9733603487");
        databaseHandler.addContact(item1);

        Contact item2 = new Contact();
        item2.setName("Rita");
        item2.setPhoneNumber("9836621728");
        databaseHandler.addContact(item2);

        Contact item3 = new Contact();
        item3.setName("Debotosh");
        item3.setPhoneNumber("7797557839");
        databaseHandler.addContact(item3);*/

        /*
        GET A SPECIFIC ITEM FROM THE TABLE
         */
        //Contact getContact = databaseHandler.getContact(2);
        //Log.i("SQL response",getContact.getName());

        /*
        UPDATE A SPECIFIC ITEM IN THE TABLE
         */
        /*Contact updateContact = databaseHandler.getContact(1);
        updateContact.setName("Shonty");
        updateContact.setPhoneNumber("9876543210");
        int idUpdated = databaseHandler.updateContact(updateContact);
        Log.i("SQL response", String.valueOf(idUpdated));*/

        /*
        DELETE AN ITEM IN THE TABLE
         */
//        Contact deleteContact = databaseHandler.getContact(3);
//        databaseHandler.deleteContact(deleteContact);

        /*
        GET ALL ITEMS FROM THE TABLE
         */
        /*
        List<Contact> contactList = databaseHandler.getAllContacts();
       for (Contact contact:contactList){
           Log.i("SQL response", String.valueOf(contact.getName()));
       }
*/

    }
}