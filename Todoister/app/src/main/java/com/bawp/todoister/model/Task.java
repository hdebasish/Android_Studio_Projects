package com.bawp.todoister.model;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    public long taskID;

    public String task;

    public Priority priority;

    @ColumnInfo(name = "due_date")
    public Date dueDate;

    @ColumnInfo(name = "created_at")
    public Date createdAt;

    @ColumnInfo(name = "is_done")
    public Boolean isDone;

    public Task(String task, Priority priority, Date dueDate, Date createdAt, Boolean isDone) {
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.isDone = isDone;
    }

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", task='" + task + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", createdAt=" + createdAt +
                ", isDone=" + isDone +
                '}';
    }
}
