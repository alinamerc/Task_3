package com.zhirova.presentation.screen.start;

import android.content.Context;
import com.zhirova.domain.NewsItem;
import java.util.List;


public class StartContract {

    public interface View{
        void updateNewsList(List<NewsItem> actualNews);
        void showLoader();
        void showInfoAboutLackOfNews();
        void showInternetError();
        void showServerError();
        void showError();
    }


    public interface Presenter{
        void subscribe(View view, boolean needUpdate, Context context);
        void unsubsribe(View view);
        void refreshNews();
    }


}
