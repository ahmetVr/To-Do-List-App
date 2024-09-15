package com.example.todolist.Model;

public class ToDoModel {
    private String task;
    private int id;
    private int status;

    public ToDoModel(String sth,int id,int val) {
        this.task = sth;
        this.id = id;
        this.status = val;
    }
    public ToDoModel() {}

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}