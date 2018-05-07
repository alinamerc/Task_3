package com.zhirova.presentation.model;


import com.zhirova.domain.NewsItem;


public class NewsItemAdapter {

    private boolean selected;
    private NewsItem newsItem;


    public NewsItemAdapter(boolean selected, NewsItem newsItem) {
        this.selected = selected;
        this.newsItem = new NewsItem();
        this.newsItem.setId(newsItem.getId());
        this.newsItem.setTitle(newsItem.getTitle());
        this.newsItem.setDescription(newsItem.getDescription());
        this.newsItem.setImage(newsItem.getImage());
        this.newsItem.setDate(newsItem.getDate());
    }


    public boolean isSelected() {
        return selected;
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
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
