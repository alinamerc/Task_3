package com.zhirova.model.loaders;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.zhirova.domain.NewsItem;
import com.zhirova.local.local_repository.LocalApi;
import com.zhirova.local.local_repository.LocalApiImpl;
import com.zhirova.remote.remote_repository.RemoteApi;
import com.zhirova.remote.remote_repository.RemoteApiImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FromNetworkLoader extends AsyncTaskLoader<List<NewsItem>> {

    private final String TAG = "FROM_NETWORK_LOADER";
    private final String url;


    public FromNetworkLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }


    @Nullable
    @Override
    public List<NewsItem> loadInBackground() {
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            Log.e(TAG, "ERROR", e);
//        }
        RemoteApi remoteApi = new RemoteApiImpl();
        List<NewsItem> news = remoteApi.loadNews(url);

        LocalApi localApi = new LocalApiImpl(getContext());
        localApi.refreshNews(news);

        return news;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


}
