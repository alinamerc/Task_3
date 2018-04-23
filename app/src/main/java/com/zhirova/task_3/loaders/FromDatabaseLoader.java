package com.zhirova.task_3.loaders;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.zhirova.task_3.application.ItemApplication;
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
        Log.d(TAG, "loadInBackground_________FromDatabaseLoader");
        return ItemApplication.localApi.getNews();
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


}
