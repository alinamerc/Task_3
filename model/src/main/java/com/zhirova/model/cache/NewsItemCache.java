package com.zhirova.model.cache;


import com.zhirova.domain.NewsItem;

import java.util.List;


public interface NewsItemCache {
    String getStatus();
    List<NewsItem> getData();
    void refreshData();
}
