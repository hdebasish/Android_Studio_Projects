package com.example.testlocation.model;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> locationLive = new MutableLiveData<>();

    public LiveData<String> getLocation() {
        return locationLive;
    }

    public void setLocation(String location) {
        locationLive.setValue(location);
    }
}
