package com.zhirova.presentation.screen.start;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.zhirova.presentation.model.NewsItemPresent;

import java.util.List;


public class StartContract {

    public interface View{
        void updateNewsList(List<NewsItemPresent> actualNews);
        void updateMessagesAndEnvironment(String status);
    }


    public interface Presenter{
        void subscribe(Context context, boolean needUpdate, Fragment view);
        void unsubsribe(View view);
        void refreshNews();
    }


}
