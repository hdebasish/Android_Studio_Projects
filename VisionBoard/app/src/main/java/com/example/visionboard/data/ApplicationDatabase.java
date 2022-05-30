package com.example.visionboard.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.visionboard.model.Board;

@Database(entities = {Board.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "board_db";
    private static ApplicationDatabase INSTANCE;
    public static ApplicationDatabase getInstance(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,
                    ApplicationDatabase.class,
                    DATABASE_NAME).build();
        }
        return INSTANCE;
    }
    public abstract BoardDao boardDao();
}
