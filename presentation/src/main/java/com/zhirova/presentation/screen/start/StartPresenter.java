package com.zhirova.presentation.screen.start;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zhirova.domain.NewsItem;
import com.zhirova.model.cache.NewsItemCache;
import com.zhirova.model.cache.NewsItemCacheImpl;
import com.zhirova.presentation.model.NewsItemPresent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class StartPresenter implements StartContract.Presenter {

    private final String TAG = "START_PRESENTER";
    private final String URL = "https://www.sport.ru/rssfeeds/news.rss";
    private StartContract.View view;
    private NewsItemCache newsItemCache;


    @Override
    public void subscribe(Context context, boolean needUpdate, Fragment view) {
        this.view = (StartContract.View) view;
        newsItemCache = new NewsItemCacheImpl(context, needUpdate, URL, view);
        updateScreen();
    }


    @Override
    public void unsubsribe(StartContract.View view) {
        this.view = null;
    }


    @Override
    public void refreshNews() {
        newsItemCache.refreshData();
        updateScreen();
    }


    private void updateScreen() {
        if (view == null) return;
        List<NewsItem> news = newsItemCache.getData();

        Log.d("DATA", "updateScreen_______news.size() = " + news.size());

        view.updateNewsList(mapper(news));
        String curStatus = newsItemCache.getStatus();
        view.updateMessagesAndEnvironment(curStatus);
    }


    private List<NewsItemPresent> mapper(List<NewsItem> news) {
        List<NewsItemPresent> transformNews = new ArrayList<>();
        for (NewsItem curNewsItem: news) {
            NewsItemPresent curNewsItemPresent = new NewsItemPresent(false, curNewsItem);
            transformNews.add(curNewsItemPresent);
        }
        return transformNews;
    }


}
