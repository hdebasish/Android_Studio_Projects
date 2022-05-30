package com.example.visionboard.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.visionboard.model.Board;

import java.util.List;

@Dao
public interface BoardDao {

    @Insert
    Long insertBoard(Board board);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Board board);

    @Query("DELETE FROM board_tbl WHERE id=:id")
    int delete(int id);

    @Query("SELECT * FROM board_tbl")
    LiveData<List<Board>> loadAllBoard();

    @Query("SELECT * FROM board_tbl WHERE id=:id")
    LiveData<Board> loadBoardById(int id);

    @Query("DELETE FROM board_tbl")
    void deleteAllBoards();
}
