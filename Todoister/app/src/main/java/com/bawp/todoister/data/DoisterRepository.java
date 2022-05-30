package com.bawp.todoister.data;

import android.app.Application;

import com.bawp.todoister.model.Task;
import com.bawp.todoister.util.TaskRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DoisterRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTask;

    public DoisterRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        taskDao = database.taskDao();
        allTask = taskDao.getTasks();
    }

    public LiveData<List<Task>> getAllTask(){
        return allTask;
    }

    public void insert(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(()-> taskDao.insert(task));
    }

    public LiveData<Task> get(long id){
        return taskDao.get(id);
    }

    public void update(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->taskDao.update(task));
    }

    public void delete(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->taskDao.delete(task));
    }
}
