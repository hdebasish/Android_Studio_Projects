package com.bawp.virustracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.WorkInfo;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bawp.virustracker.workers.VirusViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.bawp.virustracker.Constants.DATA_OUTPUT;

public class MainActivity extends AppCompatActivity {

    private VirusViewModel virusViewModel;
    private ProgressBar progressBar;
    private TextView cases;
    private TextView todayCases;
    private TextView deaths;
    private TextView todayDeaths;
    private TextView recovered;
    private TextView active;
    private TextView critical;
    private TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cases=findViewById(R.id.cases);
        todayCases=findViewById(R.id.todayCases);
        deaths=findViewById(R.id.deaths);
        todayDeaths=findViewById(R.id.todayDeaths);
        recovered=findViewById(R.id.recovered);
        active=findViewById(R.id.active);
        critical=findViewById(R.id.critical);
        progressBar=findViewById(R.id.progressBar);
        loading=findViewById(R.id.loading);

        virusViewModel=new ViewModelProvider.AndroidViewModelFactory((Application) getApplicationContext()).create(VirusViewModel.class);

        getVirusStatus();
    }

    private void getVirusStatus() {

        virusViewModel.getOutputWorkInfo().observe(this, workInfos -> {
            if (workInfos!=null){
               WorkInfo workInfo = workInfos.get(0);
                boolean finished = workInfo.getState().isFinished();

                if (!finished){
                    showWorkInProgress();
                }else {
                    Data outputData = workInfo.getOutputData();

                    String outputDataString = outputData.getString(DATA_OUTPUT);

                    if (!TextUtils.isEmpty(outputDataString)){
                        virusViewModel.setOutputData(outputDataString);
                    }

                    populateUI();
                }
            }
        });
        virusViewModel.downLoadJSON();
    }

    private void showWorkInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
    }

    private void populateUI() {

        String outputData = virusViewModel.getOutputData();
        try {
            JSONObject jsonObject = new JSONObject(outputData);
            Log.i("jsonObject", "populateUI: " + jsonObject.getString("cases"));
            cases.setText(String.format(getString(R.string.cases), jsonObject.getLong("cases")));
            todayCases.setText(String.format(getString(R.string.todayCases), jsonObject.getLong("todayCases")));
            deaths.setText(String.format(getString(R.string.deaths), jsonObject.getLong("deaths")));
            todayDeaths.setText(String.format(getString(R.string.todayDeaths), jsonObject.getLong("todayDeaths")));
            recovered.setText(String.format(getString(R.string.recovered), jsonObject.getLong("recovered")));
            active.setText(String.format(getString(R.string.active), jsonObject.getLong("active")));
            critical.setText(String.format(getString(R.string.critical), jsonObject.getLong("critical")));
            processingDone();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void processingDone() {
        progressBar.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.virus_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                getVirusStatus();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}