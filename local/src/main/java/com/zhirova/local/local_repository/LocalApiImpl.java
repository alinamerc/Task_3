package com.zhirova.local.local_repository;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhirova.common.NewsItemApplication;
import com.zhirova.domain.NewsItem;
import com.zhirova.local.database.DatabaseApi;
import com.zhirova.local.database.DatabaseHelper;

import java.util.List;


public class LocalApiImpl implements LocalApi {

    private static LocalApiImpl INSTANCE;
    private SQLiteDatabase database;


    public static LocalApiImpl getInstance(){
        if (INSTANCE == null){
            INSTANCE = new LocalApiImpl();
        }
        return INSTANCE;
    }


    @Override
    public List<NewsItem> getNews() {
        List<NewsItem> news = DatabaseApi.getAllItems(database);
        return news;
    }


    @Override
    public NewsItem getSelectedNewsItem(String id) {
        return DatabaseApi.getSelectedItem(database, id);
    }


    @Override
    public void refreshNews(List<NewsItem> news) {
        DatabaseApi.deleteAllItems(database);
        for (NewsItem curNewsItem:news) {
            DatabaseApi.addItem(curNewsItem.getId(), curNewsItem.getTitle(),
                    curNewsItem.getDescription(), curNewsItem.getImage(),
                    (int) curNewsItem.getDate(), database);
        }
    }


    private LocalApiImpl() {
        Context context = NewsItemApplication.getContext();
        database = new DatabaseHelper(context).getWritableDatabase();
    }


}
