package com.example.transientstate.model;

import androidx.lifecycle.ViewModel;

public class CounterViewModel extends ViewModel {
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
