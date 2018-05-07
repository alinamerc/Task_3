package com.zhirova.model.cache;

import android.content.Context;
import android.database.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.zhirova.domain.NewsItem;
import com.zhirova.local.local_repository.LocalApi;
import com.zhirova.local.local_repository.LocalApiImpl;
import com.zhirova.model.loaders.FromDatabaseLoader;
import com.zhirova.model.loaders.FromNetworkLoader;
import com.zhirova.remote.remote_repository.RemoteApi;
import com.zhirova.remote.remote_repository.RemoteApiImpl;

import java.util.ArrayList;
import java.util.List;


public class NewsItemCacheImpl implements NewsItemCache, LoaderManager.LoaderCallbacks<List<NewsItem>> {

    private final int LOADER_FROM_DATABASE_ID = 1;
    private final int LOADER_FROM_NETWORK_ID = 2;

    private  Context context;
    private boolean needUpdate;
    private final String url;
    private final LoaderManager loaderManager;
    private Loader<List<NewsItem>> readingLoader;

    private List<NewsItem> news = new ArrayList<>();
    private String status = "";


    public NewsItemCacheImpl(Context context, boolean needUpdate, String url, Fragment fragment) {
        this.context = context;
        this.needUpdate = needUpdate;
        this.url = url;
        loaderManager = fragment.getActivity().getSupportLoaderManager();
        loaderManager.destroyLoader(LOADER_FROM_DATABASE_ID);
        readingLoader = loaderManager.initLoader(LOADER_FROM_DATABASE_ID,null, this);


        LocalApi localApi = new LocalApiImpl(context);
        Observable<List<NewsItem>> localNews = (Observable<List<NewsItem>>) localApi.getNews();

        if (needUpdate) {
            RemoteApi remoteApi = new RemoteApiImpl();
            Observable<List<NewsItem>> remoteNews = (Observable<List<NewsItem>>) remoteApi.loadNews(url);
        }

    }


    @Override
    public String getStatus() {
        return status;
    }


    private void setStatus(String status) {
        this.status = status;
    }


    @Override
    public List<NewsItem> getData() {
        return news;
    }


    private void setData(List<NewsItem> news){
        this.news.clear();
        if (news != null){
            this.news.addAll(news);
        }
    }


    @Override
    public void refreshData() {
        readingLoader = loaderManager.restartLoader(LOADER_FROM_NETWORK_ID,null, this);
        readingLoader.forceLoad();
    }


    @NonNull
    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d("DATA", "onCreateLoader ");
        if (id == LOADER_FROM_DATABASE_ID) {
            readingLoader = new FromDatabaseLoader(context);
        }
        else if (id == LOADER_FROM_NETWORK_ID) {
            readingLoader = new FromNetworkLoader(context, url);
        }
        return readingLoader;
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsItem>> loader, List<NewsItem> data) {
        Log.d("DATA", "onLoadFinished______data.size() = " + data.size());
        if (data.size() > 0) {
            setData(data);
        }

        int id = loader.getId();
        Log.d("DATA", "onLoadFinished______id = " + id);
        Log.d("DATA", "needUpdate = " + needUpdate);

        if (id == LOADER_FROM_DATABASE_ID) {
            if (needUpdate) {
                needUpdate = false;
                readingLoader = loaderManager.initLoader(LOADER_FROM_NETWORK_ID,null, this);
                if (data.size() == 0) {
                    setStatus("EMPTY_DB");
                }
            } else {
                if (data.size() == 0) {
                    setStatus("NO_NEWS");
                }
            }
        }
        else if (id == LOADER_FROM_NETWORK_ID) {
            if (RemoteApiImpl.isOnline(context)) {
                if (data.size() == 0) {
                    setStatus("NO_NEWS");
                }
            } else {
                setStatus("NO_CONNECTION");
            }
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsItem>> loader) {
    }


}
