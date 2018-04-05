package com.zhirova.task_3.model;


import java.util.Date;
import java.util.UUID;


public class Item {

    private String id;
    private String title;
    private String description;
    private String image;
    private long date;


    public Item() {
        this.id = UUID.randomUUID().toString();
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
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


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public long getDate() {
        return date;
    }


    public void setDate(long date) {
        this.date = date;
    }


}