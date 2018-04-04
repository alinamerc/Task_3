package com.zhirova.task_3.model;


import java.util.Date;
import java.util.UUID;


public class Item {

    private final String id;
    private String title;
    private String description;
    private String image;
    private Date date;


    public Item() {
        this.id = UUID.randomUUID().toString();
    }


    public String getId() {
        return id;
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


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


}
