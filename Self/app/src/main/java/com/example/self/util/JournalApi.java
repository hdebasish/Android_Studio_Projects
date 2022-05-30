package com.example.self.util;

import android.app.Application;

public class JournalApi extends Application {

    private String username;
    private String userId;

    private static final JournalApi ourInstance = new JournalApi();

    public static JournalApi getInstance() {
        return ourInstance;
    }

    public JournalApi() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
