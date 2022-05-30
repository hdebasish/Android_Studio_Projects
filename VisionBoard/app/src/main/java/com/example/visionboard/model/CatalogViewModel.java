package com.example.visionboard.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.visionboard.data.ApplicationDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CatalogViewModel extends AndroidViewModel {
    private LiveData<List<Board>> boards;

    public CatalogViewModel(@NonNull @NotNull Application application) {
        super(application);
        ApplicationDatabase database = ApplicationDatabase.getInstance(this.getApplication());
        boards=database.boardDao().loadAllBoard();

    }
    public LiveData<List<Board>> getBoards(){return boards;}
}
