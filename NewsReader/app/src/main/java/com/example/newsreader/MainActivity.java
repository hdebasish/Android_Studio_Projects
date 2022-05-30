package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    ArrayAdapter adapter;
    SQLiteDatabase articleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String result="";
        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),Article.class);
                intent.putExtra("url",url.get(position));
                startActivity(intent);
            }
        });
        articleDB = this.openOrCreateDatabase("Articles",MODE_PRIVATE,null);
        articleDB.execSQL("CREATE TABLE IF NOT EXISTS news(id INTEGER PRIMARY KEY, articleID INTEGER, title VARCHAR, url VARCHAR)");
        updateListView();
        downloadNews task= new downloadNews();
        try {
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateListView(){
        Cursor c= articleDB.rawQuery("SELECT * FROM news",null);
        int titleIndex=c.getColumnIndex("title");
        int urlIndex=c.getColumnIndex("url");
        if(c.moveToFirst()){
            titles.clear();
            url.clear();
            do{
                titles.add(c.getString(titleIndex));
                url.add(c.getString(urlIndex));
            }while (c.moveToNext());

            adapter.notifyDataSetChanged();
        }

    }




    public class downloadNews extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data=reader.read();
                while (data!=-1){
                    char current= (char) data;
                    result+=current;
                    data=reader.read();
                }
                JSONArray jsonArray = new JSONArray(result);
                articleDB.execSQL("DELETE FROM news");
                int numberOfItems=30;
                if(jsonArray.length()<numberOfItems){
                    numberOfItems=jsonArray.length();
                }
                for(int i=0;i<numberOfItems;i++){
                    String finalResult="";
                    String articleID= jsonArray.getString(i);
                    url= new URL("https://hacker-news.firebaseio.com/v0/item/"+articleID+".json?print=pretty");
                    httpURLConnection= (HttpURLConnection) url.openConnection();
                    inputStream=httpURLConnection.getInputStream();
                    reader = new InputStreamReader(inputStream);
                    data=reader.read();
                    while (data!=-1){
                        char current=(char)data;
                        finalResult+=current;
                        data=reader.read();
                    }

                    JSONObject jsonObject=new JSONObject(finalResult);
                    if(!jsonObject.isNull("title")&&!jsonObject.isNull("url")){
                        String articleTitle=jsonObject.getString("title");
                        //Log.i("Titles Content",articleTitle);
                        String articleURL=jsonObject.getString("url");
                       /* String articleContent="";
                        url= new URL(articleURL);
                        httpURLConnection= (HttpURLConnection) url.openConnection();
                        inputStream=httpURLConnection.getInputStream();
                        reader = new InputStreamReader(inputStream);
                        data=reader.read();
                        while (data!=-1){
                            char current=(char)data;
                            articleContent+=current;
                            data=reader.read();
                        }*/
                        Log.i("URLs",articleURL);

                        String sql="INSERT INTO news(articleID,title,url) VALUES(? , ? , ?)";
                        SQLiteStatement statement=articleDB.compileStatement(sql);
                        statement.bindString(1,articleID);
                        statement.bindString(2,articleTitle);
                        statement.bindString(3,articleURL);
                        statement.execute();
                    }



                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }
    }
}
