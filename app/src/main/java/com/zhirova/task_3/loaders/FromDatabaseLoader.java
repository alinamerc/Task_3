package com.zhirova.task_3.loaders;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.zhirova.task_3.database.DatabaseApi;
import com.zhirova.task_3.database.DatabaseHelper;
import com.zhirova.task_3.model.Item;

import java.util.List;


public class FromDatabaseLoader extends AsyncTaskLoader<List<Item>> {

    private final String TAG = "START_FRAGMENT";
    private final Context context;


    public FromDatabaseLoader(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Nullable
    @Override
    public List<Item> loadInBackground() {
        Log.d(TAG, "loadInBackground_____FromDatabaseLoader");

        SQLiteDatabase database = new DatabaseHelper(context).getWritableDatabase();
        List<Item> news = DatabaseApi.getAllItems(database);
        database.close();
        return news;
    }


    @Override
    public void forceLoad() {
        super.forceLoad();
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }


    @Override
    public void deliverResult(List<Item> data) {
        super.deliverResult(data);
    }


}
