package com.example.kanbi.todolist;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kanbi on 30/12/2017.
 */

public class ToDoModel {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    @NonNull
    private String description;

    public ToDoModel() {

    }

    public ToDoModel(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public ToDoModel(ToDoModel body) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


