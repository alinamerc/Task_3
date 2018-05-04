package com.zhirova.presentation.screen.start;

import android.support.v4.app.FragmentActivity;

import com.zhirova.presentation.model.NewsItemPresent;

import java.util.List;

public class StartContract {

    interface View{
        void updateNewsList(List<NewsItemPresent> actualNews);
        void updateMessagesAndEnvironment(String status);
    }

    interface Presenter{
        void subscribe(FragmentActivity view, boolean needUpdate);
        void unsubsribe(View view);
    }


}
