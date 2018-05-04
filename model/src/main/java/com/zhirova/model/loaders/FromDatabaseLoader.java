package com.zhirova.model.loaders;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import com.zhirova.domain.NewsItem;
import com.zhirova.local.local_repository.LocalApi;
import com.zhirova.local.local_repository.LocalApiImpl;

import java.util.List;


public class FromDatabaseLoader extends AsyncTaskLoader<List<NewsItem>> {

    private final String TAG = "FROM_DATABASE_LOADER";


    public FromDatabaseLoader(@NonNull Context context) {
        super(context);
    }


    @Nullable
    @Override
    public List<NewsItem> loadInBackground() {
        return new LocalApiImpl(getContext()).getNews();
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


}
