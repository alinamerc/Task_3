package com.zhirova.task_3;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhirova.task_3.model.Item;

import java.util.List;


public class StartFragment extends Fragment {

    private final String TAG = "START_FRAGMENT";
    private final String URL = "https://www.sport.ru/rssfeeds/news.rss";

    private List<Item> news;


//    SQLiteDatabase database = new DatabaseHelper(fragment.getContext()).getWritableDatabase();
////        ItemsTable.addItem("2", "Адриано", "Адриано: Идеальным завершением сезона было бы завоевание двух трофеев",
////                "head_0.jpg", 1234234, database);
//        return ItemsTable.getAllItems(database);


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    private void print(List<Item> news) {
        for (int i = 0; i < news.size(); i++) {
            Log.d(TAG, "=====================================================");
            Log.d(TAG, "TITLE = " + news.get(i).getTitle() + "\n" +
                    "DESC = " + news.get(i).getDescription() + "\n" +
                    "IMAGE = " + news.get(i).getImage() + "\n" +
                    "DATE = " + news.get(i).getDate());
        }
    }


}
