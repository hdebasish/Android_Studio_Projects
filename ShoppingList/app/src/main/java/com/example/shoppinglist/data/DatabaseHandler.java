package com.example.shoppinglist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.example.shoppinglist.R;
import com.example.shoppinglist.model.Item;
import com.example.shoppinglist.util.Util;

import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_ITEM_NAME + " TEXT," + Util.KEY_ITEM_BRAND + " TEXT," + Util.KEY_ITEM_QUALITY + " TEXT," + Util.KEY_ITEM_QUANTITY + " TEXT," + Util.KEY_ITEM_DATE + " LONG" + ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.drop_table);
        db.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});
        onCreate(db);
    }

    public void addItem(Item item){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_ITEM_NAME,item.getItemName());
        values.put(Util.KEY_ITEM_BRAND,item.getItemBrand());
        values.put(Util.KEY_ITEM_QUALITY,item.getItemQuality());
        values.put(Util.KEY_ITEM_QUANTITY,item.getItemQuantity());
        values.put(Util.KEY_ITEM_DATE,java.lang.System.currentTimeMillis());
        db.insert(Util.TABLE_NAME,null,values);
        db.close();
    }

    public Item getItem(int id){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {Util.KEY_ID, Util.KEY_ITEM_NAME, Util.KEY_ITEM_BRAND, Util.KEY_ITEM_QUALITY, Util.KEY_ITEM_QUANTITY, Util.KEY_ITEM_DATE };
        String selection = Util.KEY_ID+"=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(Util.TABLE_NAME,projection,selection,selectionArgs,null,null,null);

        if (cursor.moveToFirst()){
            Item item = new Item();
            item.setId(Integer.parseInt(cursor.getString(0)));
            item.setItemName(cursor.getString(1));
            item.setItemBrand(cursor.getString(2));
            item.setItemQuality(cursor.getString(3));
            item.setItemQuantity(cursor.getString(4));
            Format dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(5)).getTime());
            item.setItemAdded(formattedDate);
            cursor.close();
            return item;
        }
        return null;
    }

    public List<Item> getAllItems(){
        SQLiteDatabase db = getReadableDatabase();
        List<Item> itemList = new ArrayList<>();
        String orderBy = Util.KEY_ITEM_DATE + " DESC";
        String[] projection = {Util.KEY_ID, Util.KEY_ITEM_NAME, Util.KEY_ITEM_BRAND, Util.KEY_ITEM_QUALITY, Util.KEY_ITEM_QUANTITY, Util.KEY_ITEM_DATE };
        Cursor cursor = db.query(Util.TABLE_NAME,projection,null,null,null,null,orderBy);
        if (cursor.moveToFirst()){
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setItemName(cursor.getString(1));
                item.setItemBrand(cursor.getString(2));
                item.setItemQuality(cursor.getString(3));
                item.setItemQuantity(cursor.getString(4));
                Format dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(5)).getTime());
                item.setItemAdded(formattedDate);
                itemList.add(item);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return itemList;
    }

    public int updateItem(Item item){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_ITEM_NAME,item.getItemName());
        values.put(Util.KEY_ITEM_BRAND,item.getItemBrand());
        values.put(Util.KEY_ITEM_QUALITY,item.getItemQuality());
        values.put(Util.KEY_ITEM_QUANTITY,item.getItemQuantity());
        values.put(Util.KEY_ITEM_DATE,java.lang.System.currentTimeMillis());
        return db.update(Util.TABLE_NAME,values,Util.KEY_ID+"=?",new String[]{String.valueOf(item.getId())});
    }

    public void deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME,Util.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String countQuery = "SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        return cursor.getCount();
    }
}
