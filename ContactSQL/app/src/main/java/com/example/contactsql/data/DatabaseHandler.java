package com.example.contactsql.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.contactsql.R;
import com.example.contactsql.model.Contact;
import com.example.contactsql.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table
        String CREATE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT," + Util.KEY_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
        //Log.i("SQL response","Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.drop_table);
        db.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(db);

    }

    //Add an item in the table

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());

        db.insert(Util.TABLE_NAME,null,values);
        //Log.i("SQL response","Item added");
        db.close();
    }

    //Get an item from the table

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

       // Cursor cursor =  db.rawQuery( "select * from "+Util.TABLE_NAME+" where "+Util.KEY_ID+"="+id+"", null );


        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID,Util.KEY_NAME,Util.KEY_PHONE_NUMBER},
                Util.KEY_ID+"=?",
                new String[]{String.valueOf(id)},
                null,null,null);

        //Log.i("SQL response", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            Contact contact = new Contact();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
            cursor.close();
            return contact;
        }
        return null;
    }

    //Get all items from the table

    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectALL = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectALL,null);

        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while (cursor.moveToNext());
            cursor.close();
            }
        return contactList;
        }

        //Update an item in the table

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());
        return db.update(Util.TABLE_NAME,values,Util.KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
    }

    //Delete an item from the table

    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME,Util.KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    //Count the number of items in the table
    public int getCount(){
        String countQuery = "SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        return cursor.getCount();
    }

}
