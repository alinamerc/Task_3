package com.zhirova.presentation.model;


import com.zhirova.domain.NewsItem;


public class NewsItemPresent {

    private boolean isSelected;
    private NewsItem newsItem;


    public NewsItemPresent(boolean isSelected, NewsItem newsItem) {
        this.isSelected = isSelected;
        this.newsItem = newsItem;
    }


    public boolean isSelected() {
        return isSelected;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getId() {
        return newsItem.getId();
    }


    public String getTitle() {
        return newsItem.getTitle();
    }


    public String getDescription() {
        return newsItem.getDescription();
    }


    public String getImage() {
        return newsItem.getImage();
    }


    public long getDate() {
        return newsItem.getDate();
    }


}
