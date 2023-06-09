package com.example.myapplication;

public class TodoItem {

    private int id;
    private String text;
    private boolean completed;

    public TodoItem(String text) {
        this.text = text;
        this.completed = false;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

