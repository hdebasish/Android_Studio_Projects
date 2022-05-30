package com.bawp.virustracker.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CleanupWorker extends Worker {
    private static final String CLEANING_MSG = "CLEAN";

    public CleanupWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = new Data.Builder().putString(CLEANING_MSG,"Cleaning...").build();
        return Result.success(data);
    }
}
