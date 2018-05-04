package com.zhirova.presentation.screen.start;


import android.support.v4.app.FragmentActivity;

import com.zhirova.domain.NewsItem;
import com.zhirova.model.cache.NewsItemCache;
import com.zhirova.model.cache.NewsItemCacheImpl;
import com.zhirova.presentation.model.NewsItemPresent;

import java.util.ArrayList;
import java.util.List;


public class StartPresenter implements StartContract.Presenter{


    private StartContract.View view;
    private NewsItemCache newsItemCache;

    private final static String url = "";


    public StartPresenter() {
     //   newsItemCache = new NewsItemCacheImpl();
    }


    public void refreshNews() {
        newsItemCache.refreshData();
        updateScreen();
    }


    public List<NewsItemPresent> getNews() {
        List<NewsItem> news = newsItemCache.getData();
        return mapper(news);
    }


    private void updateScreen() {
        if(view == null) return;
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


    @Override
    public void subscribe(FragmentActivity view, boolean needUpdate) {
        this.view = view;
        newsItemCache = new NewsItemCacheImpl(needUpdate, url, (FragmentActivity) view);
        updateScreen();
    }

    @Override
    public void unsubsribe(StartContract.View view) {
        this.view = null;
    }
}
