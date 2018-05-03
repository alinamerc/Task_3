package com.zhirova.task_3.data_repository;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhirova.task_3.database.DatabaseApi;
import com.zhirova.task_3.database.DatabaseHelper;
import com.zhirova.task_3.model.NewsItem;

import java.util.List;


public class LocalApi {

    private static LocalApi INSTANCE;
    private SQLiteDatabase database;


    public static LocalApi getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new LocalApi(context);
        }
        return INSTANCE;
    }


    public NewsItem getSelectedNews(String id) {
        return DatabaseApi.getSelectedItem(database, id);
    }


    public List<NewsItem> getNews() {
        List<NewsItem> news = DatabaseApi.getAllItems(database);
        return news;
    }


    public void updateNews(List<NewsItem> news) {
        DatabaseApi.deleteAllItems(database);
        for (NewsItem curItem:news) {
            DatabaseApi.addItem(curItem.getId(), curItem.getTitle(), curItem.getDescription(),
                    curItem.getImage(), (int) curItem.getDate(), database);
        }
    }


    private LocalApi(Context context) {
        database = new DatabaseHelper(context).getWritableDatabase();
    }


}
