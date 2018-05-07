package com.zhirova.model;


import com.zhirova.domain.DataContainer;
import com.zhirova.domain.NewsItem;

import java.util.List;

import io.reactivex.Observable;


public interface NewsModel {
    Observable<DataContainer<List<NewsItem>>> getNews(boolean needUpdate);
}
