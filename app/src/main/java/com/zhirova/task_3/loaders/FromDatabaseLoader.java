package com.zhirova.task_3.loaders;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.zhirova.task_3.application.ItemApplication;
import com.zhirova.task_3.model.NewsItem;

import java.util.List;


public class FromDatabaseLoader extends AsyncTaskLoader<List<NewsItem>> {

    private final String TAG = "FROM_DATABASE_LOADER";


    public FromDatabaseLoader(@NonNull Context context) {
        super(context);
    }


    @Nullable
    @Override
    public List<NewsItem> loadInBackground() {
        return ItemApplication.localApi.getNews();
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


}
