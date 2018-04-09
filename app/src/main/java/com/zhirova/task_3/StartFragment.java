package com.zhirova.task_3;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhirova.task_3.adapter.ItemsAdapter;
import com.zhirova.task_3.database.DatabaseApi;
import com.zhirova.task_3.database.DatabaseHelper;
import com.zhirova.task_3.database.ItemContract;
import com.zhirova.task_3.diff_util.ItemDiffUtilCallback;
import com.zhirova.task_3.loaders.FromDatabaseLoader;
import com.zhirova.task_3.loaders.FromNetworkLoader;
import com.zhirova.task_3.model.Item;

import java.util.ArrayList;
import java.util.List;


public class StartFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Item>>,
        ItemsAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = "START_FRAGMENT";
    private final String SAVE_FLAG = "CHECK_ON_FIRST_LOADING";
    private final String URL = "https://www.sport.ru/rssfeeds/news.rss";

    private final int LOADER_FROM_DATABASE_ID = 1;
    private final int LOADER_FROM_NETWORK_ID = 2;

    private Loader<List<Item>> readingLoader;
    private SQLiteDatabase database;
    SharedPreferences preferences;
    private List<Item> oldNews = null;
    private boolean isFirstLoadingFromDatabase;

    private ItemsAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            isFirstLoadingFromDatabase = true;
        } else {
            isFirstLoadingFromDatabase = savedInstanceState.getBoolean(SAVE_FLAG);
        }
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initData();
    }


    @Override
    public void onResume() {
        super.onResume();
        readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_DATABASE_ID, null, this);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_FLAG, isFirstLoadingFromDatabase);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (getActivity().getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            database.close();
        }
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
            if (oldNews == null) {
                oldNews = data;
            }
            updateRecycleView(data);
            oldNews = data;

            if (isFirstLoadingFromDatabase) {
                isFirstLoadingFromDatabase = false;
                readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_NETWORK_ID, null, this);
            }
        }
        else if (id == LOADER_FROM_NETWORK_ID) {
            updateTable(data);
            readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_DATABASE_ID, null, this);
            readingLoader.forceLoad();
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<Item>> loader) {
    }


    @Override
    public void onClick(Item item) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DetailFragment curFragment = DetailFragment.create(item.getId());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, curFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.postDelayed(() -> {
            swipeRefreshLayout.setRefreshing(false);
            readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_NETWORK_ID, null, this);
            readingLoader.forceLoad();
        }, 2000);
    }


    private void initUI() {
        recyclerView = getActivity().findViewById(R.id.recycler_view_items);
        swipeRefreshLayout = getActivity().findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    private void initData() {
        adapter = new ItemsAdapter(getContext());
        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        database = new DatabaseHelper(getContext()).getWritableDatabase();
    }


    private void updateTable(List<Item> news) {
        DatabaseApi.deleteAllItems(database);
        for (Item curItem:news) {
            DatabaseApi.addItem(curItem.getId(), curItem.getTitle(), curItem.getDescription(),
                    curItem.getImage(), (int) curItem.getDate(), database);
        }
        //database.close();
    }


    private void updateRecycleView(List<Item> actualNews) {
        List<Item> oldList = new ArrayList<>(oldNews);
        List<Item> newList = new ArrayList<>(actualNews);

        ItemDiffUtilCallback itemDiffUtilCallback = new ItemDiffUtilCallback(oldList, newList);
        DiffUtil.DiffResult itemDiffResult = DiffUtil.calculateDiff(itemDiffUtilCallback, true);
        adapter.setData(actualNews);
        itemDiffResult.dispatchUpdatesTo(adapter);
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
