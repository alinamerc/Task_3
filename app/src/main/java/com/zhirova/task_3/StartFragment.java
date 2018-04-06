package com.zhirova.task_3;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhirova.task_3.database.DatabaseApi;
import com.zhirova.task_3.database.DatabaseHelper;
import com.zhirova.task_3.loaders.FromDatabaseLoader;
import com.zhirova.task_3.loaders.FromNetworkLoader;
import com.zhirova.task_3.model.Item;
import com.zhirova.task_3.network.RemoteApi;

import java.util.List;


public class StartFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Item>> {

    private final String TAG = "START_FRAGMENT";
    private final String URL = "https://www.sport.ru/rssfeeds/news.rss";

    private final int LOADER_FROM_DATABASE_ID = 1;
    private final int LOADER_FROM_NETWORK_ID = 2;

    private Loader<List<Item>> readingLoader;
    private boolean isFirstLoadingFromDatabase = true;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        //readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_DATABASE_ID, null, this);
        Log.d(TAG, "onResume");
    }


    @NonNull
    @Override
    public Loader<List<Item>> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LOADER_FROM_DATABASE_ID) {
            readingLoader = new FromDatabaseLoader(getContext());
        }
        else if (id == LOADER_FROM_NETWORK_ID) {
            readingLoader = new FromNetworkLoader(getContext(), URL);
        }
        return readingLoader;
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<Item>> loader, List<Item> data) {
        int id = loader.getId();
        if (id == LOADER_FROM_DATABASE_ID) {
            if (isFirstLoadingFromDatabase) {
                isFirstLoadingFromDatabase = false;
                readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_NETWORK_ID, null, this);
            } else {
                print(data);
            }
        }
        else if (id == LOADER_FROM_NETWORK_ID) {
            updateDatabase(data);
            readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_DATABASE_ID, null, this);
            readingLoader.forceLoad();
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<Item>> loader) {
    }


    private void updateDatabase(List<Item> news) {
        SQLiteDatabase database = new DatabaseHelper(getContext()).getWritableDatabase();
        DatabaseApi.deleteAllItems(database);
        for (Item curItem:news) {
            DatabaseApi.addItem(curItem.getId(), curItem.getTitle(), curItem.getDescription(),
                    curItem.getImage(), (int) curItem.getDate(), database);
        }
        database.close();
    }


    private void print(List<Item> news) {
        for (int i = 0; i < news.size(); i++) {
            Log.d(TAG, "==========================================");
            Log.d(TAG, "TITLE = " + news.get(i).getTitle() + "\n" +
                    "DESC = " + news.get(i).getDescription() + "\n" +
                    "IMAGE = " + news.get(i).getImage() + "\n" +
                    "DATE = " + news.get(i).getDate());
        }
    }


}
