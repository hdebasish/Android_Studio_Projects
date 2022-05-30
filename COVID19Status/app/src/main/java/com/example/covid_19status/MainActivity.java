package com.example.covid_19status;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<String> COUNTRIES;
    Button button2;
    TextView textView;
    TextView display;
    String confirmed = "";
    String deaths = "";
    String recovered = "";
    String result;
    String country;
    String newCases = "";
    String newDeaths = "";
    String active = "";
    String critical = "";
    String casesPerOneMillion = "";
    String world;
    AutoCompleteTextView acText;
    String item;


    public class covidData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                    result.append("\n");
                }
                return String.valueOf(result);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "No results found!", LENGTH_LONG).show();
                e.printStackTrace();
                return null;
            }

        }
    }

    public class covidWorldData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                    result.append("\n");
                }
                return String.valueOf(result);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "No results found!", LENGTH_LONG).show();
                e.printStackTrace();
                return null;
            }

        }
    }


    public void world(View view) {

        button2.setVisibility(View.INVISIBLE);
        worldData();

    }

    public void worldData() {
        JSONObject worldJSON = null;
        try {
            worldJSON = new JSONObject(world);
            confirmed = worldJSON.getString("cases");
            deaths = worldJSON.getString("deaths");
            recovered = worldJSON.getString("recovered");
            Log.i("World", confirmed);
            String message = "";
            message = "World " + "\r\n" + "Confirmed : " + confirmed + "\r\n" + "Deaths : " + deaths + "\r\n" + "Recovered : " + recovered;
            display.setText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        display = findViewById(R.id.display);
        button2 = findViewById(R.id.button2);
        button2.setVisibility(View.INVISIBLE);
        acText = findViewById(R.id.editText);
        covidData task = new covidData();
        covidWorldData task2 = new covidWorldData();
        try {
            result = task.execute("https://coronavirus-19-api.herokuapp.com/countries/").get();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Check your internet connection!", LENGTH_LONG).show();
            e.printStackTrace();
        }
        try {
            world = task2.execute("https://coronavirus-19-api.herokuapp.com/all").get();
            JSONObject worldJSON = new JSONObject(world);
            confirmed = worldJSON.getString("cases");
            deaths = worldJSON.getString("deaths");
            recovered = worldJSON.getString("recovered");
            Log.i("World", confirmed);
            String message = "";
            message = "World " + "\r\n" + "Confirmed : " + confirmed + "\r\n" + "Deaths : " + deaths + "\r\n" + "Recovered : " + recovered;
            display.setText(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = new JSONArray(result);
            COUNTRIES = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) {
                country = jsonArray.getString(i);
                JSONObject jObject = new JSONObject(country);
                COUNTRIES.add(jObject.getString("country"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COUNTRIES);
        acText.setAdapter(adapter);
        acText.setOnItemClickListener((AdapterView.OnItemClickListener) this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        item = parent.getItemAtPosition(position).toString();
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(acText.getWindowToken(), 0);
        try {

            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {

                country = jsonArray.getString(i);
                JSONObject jsonObject = new JSONObject(country);
                if (item.equals(jsonObject.getString("country"))) {
                    jsonObject = new JSONObject(country);
                    confirmed = jsonObject.getString("cases");
                    deaths = jsonObject.getString("deaths");
                    recovered = jsonObject.getString("recovered");
                    newCases = jsonObject.getString("todayCases");
                    newDeaths = jsonObject.getString("todayDeaths");
                    active = jsonObject.getString("active");
                    critical = jsonObject.getString("critical");
                    casesPerOneMillion = jsonObject.getString("casesPerOneMillion");
                    String message = "";
                    message = "Country : " + item + "\r\n" + "Confirmed : " + confirmed + "\r\n" + "Deaths : " + deaths + "\r\n" + "Recovered : " + recovered + "\r\n" + "New cases : " + newCases + "\r\n" + "New deaths : " + newDeaths + "\r\n" + "Active cases : " + active + "\r\n" + "Critical : " + critical + "\r\n" + "Cases per million : " + casesPerOneMillion;
                    display.setText(message);
                    button2.setVisibility(View.VISIBLE);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}



