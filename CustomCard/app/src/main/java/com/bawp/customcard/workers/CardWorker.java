package com.bawp.customcard.workers;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import static com.bawp.customcard.Constants.CUSTOM_QUOTE;
import static com.bawp.customcard.Constants.KEY_IMAGE_URI;

public class CardWorker extends Worker {

    public static final String TAG = CardWorker.class.getSimpleName();
    public CardWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();
        CardWorkerUtils.makeStatusNotification("Writing quote onto Image",
                applicationContext);
        CardWorkerUtils.sleep();

        String imageResourceUri = getInputData().getString(KEY_IMAGE_URI);
        String quote = getInputData().getString(CUSTOM_QUOTE);

        ContentResolver contentResolver = applicationContext.getContentResolver();

        //Create the bitmap
        try {
            Bitmap photo = BitmapFactory.decodeStream(
                    contentResolver.openInputStream(Uri.parse(imageResourceUri)));

            // Write text to Image
            Bitmap output = CardWorkerUtils.overlayTextOnBitmap(photo, applicationContext,quote);

            // Write bitmap to a temp file
            Uri outputUri = CardWorkerUtils.writeBitmapToFile(applicationContext, output);

            Data outputData = new Data.Builder()
                    .putString(KEY_IMAGE_URI, outputUri.toString())
                    .build();


            return Result.success(outputData);

        } catch (Throwable e) {
            Log.i(TAG, "doWork: Error writing quote onto image");
            return  Result.failure();
        }

    }
}
