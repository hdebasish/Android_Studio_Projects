package com.example.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    NotificationManagerCompat notificationManagerCompat;
    EditText title;
    EditText message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=findViewById(R.id.editTextTitle);
        message=findViewById(R.id.editTextMessage);
        notificationManagerCompat = NotificationManagerCompat.from(this);

    }

    public void channel1(View view){
        String titleText = title.getText().toString();
        String messageText= message.getText().toString();
        Notification notification = new NotificationCompat.Builder(this,App.CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_looks_one_black_24dp)
                .setContentTitle(titleText)
                .setContentText(messageText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1,notification);
    }

    public void channel2(View view){
        String titleText = title.getText().toString();
        String messageText= message.getText().toString();
        Notification notification = new NotificationCompat.Builder(this,App.CHANNEL_ID_2)
                .setSmallIcon(R.drawable.ic_looks_two_black_24dp)
                .setContentTitle(titleText)
                .setContentText(messageText)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManagerCompat.notify(2,notification);
    }
}
