package com.example.retrofitexample;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiCaller {

    String BASE_URL = "https://coronavirus-19-api.herokuapp.com/countries/";

    @GET("World")
    Call<World> getData();
}
