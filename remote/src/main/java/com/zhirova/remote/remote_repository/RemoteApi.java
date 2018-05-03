package com.zhirova.remote.remote_repository;


import com.zhirova.domain.NewsItem;

import java.util.List;


public interface RemoteApi {
    List<NewsItem> loadNews(String urlString);
}
