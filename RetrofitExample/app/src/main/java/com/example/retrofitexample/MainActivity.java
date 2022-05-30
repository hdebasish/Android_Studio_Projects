package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String responseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiCaller.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCaller apiCaller = retrofit.create(ApiCaller.class);
        Call<World> worldDataCall = apiCaller.getData();

        worldDataCall.enqueue(new Callback<World>() {
            @Override
            public void onResponse(Call<World> call, Response<World> response) {
                StringBuilder builder = new StringBuilder();
                if (response.isSuccessful()){
                    builder.append(response.body().getCases())
                            .append("\n")
                            .append(response.body().getDeaths())
                            .append("\n")
                            .append(response.body().getRecovered())
                            .append("\n")
                            .append(response.body().getTodayCases())
                            .append("\n")
                            .append(response.body().getTodayDeaths())
                            .append("\n")
                            .append(response.body().getTotalTests());
                    responseString = builder.toString();

                    Log.i("responseStr",responseString);
                }else {
                    Log.i("responseStr","failed");
                }

            }

            @Override
            public void onFailure(Call<World> call, Throwable t) {
                responseString = "Error processing request!";
            }
        });

     //   Log.i("responseStr",responseString);

    }
}