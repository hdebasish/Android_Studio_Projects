package com.example.pushnotificationback4app;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", "843547583669");
        installation.saveInBackground();
    }

}