package com.example.myjobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeepJobService extends JobService {

    public static final String MACTION = "com.example.myjobscheduler.INTENT" ;
    public static final String EMPLOYEENAME = "com.example.myjobscheduler.EMPLOYEENAME" ;
    Retrofit retrofit;
    ApiCaller apiCaller;
    Call<EmployeeData> employeeDataCall;

    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }



    private void doBackgroundWork(JobParameters parameters){

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiCaller.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiCaller = retrofit.create(ApiCaller.class);

         employeeDataCall = apiCaller.getData();

        employeeDataCall.enqueue(new Callback<EmployeeData>() {
            @Override
            public void onResponse(Call<EmployeeData> call, Response<EmployeeData> response) {
                Intent intent = new Intent(MACTION);

                for (Data load: Objects.requireNonNull(response.body()).getData()){
                    Log.d("data32",load.getEmployeeName());
                    intent.putExtra(EMPLOYEENAME,load.getEmployeeName());
                    sendBroadcast(intent);
                }

            }

            @Override
            public void onFailure(Call<EmployeeData> call, Throwable t) {
                Log.e("data32", "onFailure: "+t.getMessage());
            }
        });

        jobFinished(parameters,false);
    }
}
