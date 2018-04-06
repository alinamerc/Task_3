package com.zhirova.task_3.loaders;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.zhirova.task_3.model.Item;
import com.zhirova.task_3.network.RemoteApi;

import java.util.List;


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
        Log.d(TAG, "loadInBackground_____FromNetworkLoader");

        List<Item> news = RemoteApi.loadNews(url);
        return news;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


}
