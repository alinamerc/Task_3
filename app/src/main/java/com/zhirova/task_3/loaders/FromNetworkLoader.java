package com.zhirova.task_3.loaders;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.zhirova.task_3.application.ItemApplication;
import com.zhirova.task_3.model.Item;
import com.zhirova.task_3.data_repository.RemoteApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FromNetworkLoader extends AsyncTaskLoader<List<Item>> {

    private final String TAG = "START_FRAGMENT";
    private Context context;
    private String url;


    public FromNetworkLoader(@NonNull Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
    }


    @Nullable
    @Override
    public List<Item> loadInBackground() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Log.e(TAG, "ERROR", e);
        }
        List<Item> news = new ArrayList<>();
        if (RemoteApi.isOnline(context)) {
            news = RemoteApi.loadNews(url);
            ItemApplication.localApi.updateNews(news);
        }
        return news;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


}
