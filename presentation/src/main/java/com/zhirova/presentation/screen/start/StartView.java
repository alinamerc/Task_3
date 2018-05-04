package com.zhirova.presentation.screen.start;


import com.zhirova.presentation.model.NewsItemPresent;

import java.util.List;


public interface StartView {
    void updateNewsList(List<NewsItemPresent> news);
    void updateMessagesAndEnvironment(String status);
}
