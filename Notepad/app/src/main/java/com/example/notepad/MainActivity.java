package com.example.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static ArrayList<String> arrayList= new ArrayList<>();;
    static ArrayAdapter<String> adapter;
    int counter=0;
    SharedPreferences sharedPreferences;
    static SQLiteDatabase database;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

         Intent intent = new Intent(getApplicationContext(),InputText.class);
         intent.putExtra("position",arrayList.size());
         startActivity(intent);
         return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        database=this.openOrCreateDatabase("Notes",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS notes(position VARCHAR,text VARCHAR)");

        arrayList.clear();
        updateTable();

        if(arrayList.isEmpty()){
            arrayList.add("Write your note!");
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),InputText.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Do you want to delete this note?")
                        .setMessage("This note will be deleted permanently")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Toast.makeText(getApplicationContext(),"Deleted successfully!",Toast.LENGTH_SHORT).show();
                                try {

                                    adapter.notifyDataSetChanged();
                                    String delete="DELETE FROM notes WHERE position="+position+"";
                                    database.execSQL(delete);

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                arrayList.remove(position);
                                String update="UPDATE notes SET position = position-1 WHERE position>"+position+"";
                                database.execSQL(update);

                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
                return true;
            }
        });
    }

    public void updateTable(){
        try {
            Cursor c = database.rawQuery("SELECT * FROM notes ORDER BY position ASC",null);

            int positionIndex=c.getColumnIndex("position");
            int textIndex=c.getColumnIndex("text");

            c.moveToFirst();
            while (c!=null){
                String pos=c.getString(positionIndex);
                String text=c.getString(textIndex);
                arrayList.add(Integer.parseInt(pos),text);

                c.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
