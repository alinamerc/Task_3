package com.zhirova.presentation.screen.start;


import android.support.v4.app.FragmentActivity;

import com.zhirova.domain.NewsItem;
import com.zhirova.model.cache.NewsItemCache;
import com.zhirova.model.cache.NewsItemCacheImpl;
import com.zhirova.presentation.model.NewsItemPresent;

import java.util.ArrayList;
import java.util.List;


public class StartPresenter {

    private final StartView startView;
    private final NewsItemCache newsItemCache;


    public StartPresenter(StartView startView, boolean isForce, String url) {
        this.startView = startView;
        newsItemCache = new NewsItemCacheImpl(isForce, url, (FragmentActivity) startView);
        updateScreen();
    }


    public void refreshNews() {
        newsItemCache.refreshData();
        updateScreen();
    }


    private void updateScreen() {
        List<NewsItem> news = newsItemCache.getData();
        startView.updateNewsList(mapper(news));

        String curStatus = newsItemCache.getStatus();
        startView.updateMessagesAndEnvironment(curStatus);
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
