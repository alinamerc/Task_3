package com.zhirova.task_3.data_repository;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhirova.task_3.database.DatabaseApi;
import com.zhirova.task_3.database.DatabaseHelper;
import com.zhirova.task_3.model.Item;

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


    public List<Item> getNews() {
        List<Item> news = DatabaseApi.getAllItems(database);
        return news;
    }


    public void updateNews(List<Item> news) {
        DatabaseApi.deleteAllItems(database);
        for (Item curItem:news) {
            DatabaseApi.addItem(curItem.getId(), curItem.getTitle(), curItem.getDescription(),
                    curItem.getImage(), (int) curItem.getDate(), database);
        }
    }


    private LocalApi(Context context) {
        database = new DatabaseHelper(context).getWritableDatabase();
    }


}
