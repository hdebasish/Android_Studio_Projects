package com.bawp.virustracker.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.bawp.virustracker.Constants.DATA_OUTPUT;
import static com.bawp.virustracker.Constants.STRING_URL;

public class DownloadJsonWorker extends Worker {
    public DownloadJsonWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String inputURL = getInputData().getString(STRING_URL);
        Log.i("inputURL",inputURL);
        try {
            Data outputData = new Data.Builder().putString(DATA_OUTPUT,VirusWorkerUtils.processJson(inputURL)).build();
            return Result.success(outputData);
        }catch (Exception e){
            Log.i("Err",e.getMessage());
            return Result.failure();
        }

    }
}
