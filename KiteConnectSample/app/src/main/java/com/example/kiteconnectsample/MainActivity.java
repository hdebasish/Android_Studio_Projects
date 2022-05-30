package com.example.kiteconnectsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.zerodhatech.kiteconnect.KiteConnect;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class connectURL extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String result="";
            URL url;
            HttpURLConnection httpURLConnection=null;
            try {
                url=new URL(strings[0]);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KiteConnect kiteSdk = new KiteConnect("oatcp2dcq0bgh7z5");
        kiteSdk.setUserId("ZI2762");
        String url = kiteSdk.getLoginURL();
        connectURL connect = new connectURL();

        try {
            String urlData = connect.execute(url).get();
            System.out.println(urlData);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}