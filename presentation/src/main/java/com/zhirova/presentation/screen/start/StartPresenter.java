package com.zhirova.presentation.screen.start;


import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.zhirova.domain.NewsItem;
import com.zhirova.model.cache.NewsItemCache;
import com.zhirova.model.cache.NewsItemCacheImpl;
import com.zhirova.presentation.model.NewsItemPresent;

import java.util.ArrayList;
import java.util.List;


public class StartPresenter implements StartContract.Presenter {

    private final String URL = "https://www.sport.ru/rssfeeds/news.rss";
    private StartContract.View view;
    private NewsItemCache newsItemCache;


    @Override
    public void subscribe(Context context, boolean needUpdate, StartContract.View view) {
        this.view = view;
        newsItemCache = new NewsItemCacheImpl(context, needUpdate, URL, (FragmentActivity) view);
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
