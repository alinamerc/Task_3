package com.zhirova.task_3;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhirova.task_3.data_reading.DownloadXmlTask;
import com.zhirova.task_3.model.Rss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartFragment extends Fragment {

    private final String TAG = "START_FRAGMENT";
    private final String URL = "https://www.sport.ru/rssfeeds/news.rss";
    private List<Rss> news;
    private DownloadXmlTask downloadXmlTask;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (news == null){
            loadPage();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (downloadXmlTask != null){
            downloadXmlTask.cancel(true);
            downloadXmlTask = null;
        }
    }


    public void loadPage() {
//        if((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) {
//            new DownloadXmlTask().execute(URL);
//        }
//        else if ((sPref.equals(WIFI)) && (wifiConnected)) {
//            new DownloadXmlTask().execute(URL);
//        } else {
//            // show error
//        }
        downloadXmlTask = new DownloadXmlTask(this);
        downloadXmlTask.execute(URL);
    }


    public void dataBinding(List<Rss> news){
        this.news = news;
        downloadXmlTask = null;
        if (news.size() == 0) {
        } else {
            //Collections.sort(contacts, (contact1, contact2) -> contact1.getName().compareToIgnoreCase(contact2.getName()));
            for (int i = 0; i < news.size(); i++) {
                Log.d(TAG, "=====================================================");
                Log.d(TAG, "TITLE = " + news.get(i).getTitle() + "\n" +
                        "DESC = " + news.get(i).getDescription() + "\n" +
                        "IMAGE = " + news.get(i).getImage());
            }
        }
    }


}
