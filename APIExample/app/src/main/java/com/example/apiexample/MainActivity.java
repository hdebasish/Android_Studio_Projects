package com.example.apiexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class downloadAPI extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection httpURLConnection=null;
            try {
                url=new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1)
                {

                   char current= (char) data;
                  result+=current;
                  data=reader.read();
                }return result;

            } catch (IOException e) {
                e.printStackTrace();
                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject();
                String weather = jsonObject.getString("china");
                JSONArray jsonArray =  new JSONArray(weather);
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    Log.i("Country :",jsonPart.getString("cases"));
                   // Log.i("description",jsonPart.getString("description"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadAPI task = new downloadAPI();
        task.execute("https://coronavirus-19-api.herokuapp.com/countries/");

    }
}
