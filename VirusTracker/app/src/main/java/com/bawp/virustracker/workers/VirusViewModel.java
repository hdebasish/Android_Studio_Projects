package com.bawp.virustracker.workers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.bawp.virustracker.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.bawp.virustracker.Constants.API_URL;
import static com.bawp.virustracker.Constants.JSON_PROCESSING_WORK_NAME;

public class VirusViewModel extends AndroidViewModel {

    public static final String STRING_URL = "api_url";
    private static final String TAG_OUTPUT = "TAG_OUTPUT";
    private WorkManager workManager;
    private LiveData<List<WorkInfo>> saveWorkInfo;
    private String outputData;

    public VirusViewModel(@NonNull @NotNull Application application) {
        super(application);

        workManager = WorkManager.getInstance(application);
        saveWorkInfo = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT);
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }

    public String getOutputData() {
        return outputData;
    }

    public LiveData<List<WorkInfo>> getOutputWorkInfo(){
        return saveWorkInfo;
    }

    public void downLoadJSON(){
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        WorkContinuation continuation = workManager.beginUniqueWork(
                JSON_PROCESSING_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker.class)
        );

        OneTimeWorkRequest download = new OneTimeWorkRequest.Builder(DownloadJsonWorker.class)
                .setConstraints(constraints)
                .addTag(TAG_OUTPUT)
                .setInputData(createInputURL()).build();

        continuation=continuation.then(download);
        continuation.enqueue();
    }

    void cancelWork(){
        workManager.cancelUniqueWork(JSON_PROCESSING_WORK_NAME);
    }

    private Data createInputURL (){
        Data.Builder builder = new Data.Builder();
        builder.putString(STRING_URL,API_URL);
        return builder.build();
    }
}
