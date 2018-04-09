package com.zhirova.task_3.loaders;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.zhirova.task_3.model.Item;
import com.zhirova.task_3.network.RemoteApi;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class FromNetworkLoader extends AsyncTaskLoader<List<Item>> {

    private final String TAG = "START_FRAGMENT";
    private String url;


    public FromNetworkLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }


    @Nullable
    @Override
    public List<Item> loadInBackground() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Log.e(TAG, "ERROR", e);
        }
        Log.d(TAG, "loadInBackground_________FromNetworkLoader");
        List<Item> news = RemoteApi.loadNews(url);
        return news;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


}
