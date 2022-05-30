package com.bawp.virustracker.workers;

import android.util.Log;

import androidx.annotation.WorkerThread;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VirusWorkerUtils {

    private static String TAG = VirusWorkerUtils.class.getSimpleName();


    @WorkerThread
    public static String processJson(String inputUrl) {
        try {
            URL url = new URL(inputUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) throw new RuntimeException("HttpResponse failed " +
                    responseCode);
            else {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder stringBuilder = new StringBuilder();
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());
                }
                Log.i(TAG, "processJson: " + stringBuilder.toString());
                scanner.close();

                return stringBuilder.toString();
            }
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
