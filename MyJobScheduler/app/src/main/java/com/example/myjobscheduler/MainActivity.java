package com.example.myjobscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int JOB_ID = 545345;
    JobScheduler jobScheduler;
    JobInfo jobInfo;
    TextView textView;
    StringBuilder builder = new StringBuilder();;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                String employeeName = bundle.getString(DeepJobService.EMPLOYEENAME);
                 builder.append(employeeName).append("\n");
                textView.setText(builder.toString());
                Log.d("brxt",builder.toString());
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver,new IntentFilter(DeepJobService.MACTION));

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        jobScheduler=(JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        jobInfo=new JobInfo.Builder(JOB_ID,new ComponentName(getApplicationContext(),DeepJobService.class))
                //.setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
               // .setPersisted(true)
                //.setPeriodic(60*1000)
                .build();

        jobScheduler.schedule(jobInfo);


    }
}