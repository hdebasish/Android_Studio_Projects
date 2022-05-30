package com.example.notificationdemo;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_ID_1 ="Channel 1";
    public static final String CHANNEL_ID_2 ="Channel 2";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    public void createNotificationChannels(){

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel channel1=new NotificationChannel(
                    CHANNEL_ID_1,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationChannel channel2=new NotificationChannel(
                    CHANNEL_ID_2,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("This is Channel 2");

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);

        }
    }
}
