package com.zhirova.local.local_repository;


import com.zhirova.domain.NewsItem;

import java.util.List;


public interface LocalApi {
    List<NewsItem> getNews();
    NewsItem getSelectedNewsItem(String id);
    void refreshNews(List<NewsItem> news);
}
