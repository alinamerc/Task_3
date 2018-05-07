package com.zhirova.model;

import android.content.Context;
import android.util.Log;

import com.zhirova.domain.DataContainer;
import com.zhirova.domain.NewsItem;
import com.zhirova.local.local_repository.LocalApi;
import com.zhirova.local.local_repository.LocalApiImpl;
import com.zhirova.remote.remote_repository.RemoteApi;
import com.zhirova.remote.remote_repository.RemoteApiImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NewsModelImpl implements NewsModel {

    private final String TAG = "NEWS_MODEL";
    private LocalApi localApi;
    private RemoteApi remoteApi;


    public NewsModelImpl(Context context) {
        localApi = new LocalApiImpl(context);
        remoteApi = new RemoteApiImpl();
    }


    @Override
    public Observable<DataContainer<List<NewsItem>>> getNews(boolean needUpdate) {
        return Observable.<DataContainer<List<NewsItem>>>create(emitter -> {
            try {
                List<NewsItem> newsItems = localApi.getNews();
                emitter.onNext(new DataContainer<>(newsItems, DataContainer.Source.LOCAL));

                if (needUpdate || newsItems.size() == 0) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "ERROR", e);
                    }
                    newsItems = remoteApi.loadNews();
                    localApi.refreshNews(newsItems);
                    emitter.onNext(new DataContainer<>(newsItems, DataContainer.Source.REMOTE));
                }

                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
