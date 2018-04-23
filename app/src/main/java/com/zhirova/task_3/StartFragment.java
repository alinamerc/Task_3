package com.zhirova.task_3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhirova.task_3.adapter.ItemsAdapter;
import com.zhirova.task_3.application.ItemApplication;
import com.zhirova.task_3.data_repository.RemoteApi;
import com.zhirova.task_3.database.DatabaseApi;
import com.zhirova.task_3.database.DatabaseHelper;
import com.zhirova.task_3.diff_util.ItemDiffUtilCallback;
import com.zhirova.task_3.loaders.FromDatabaseLoader;
import com.zhirova.task_3.loaders.FromNetworkLoader;
import com.zhirova.task_3.model.Item;

import java.util.ArrayList;
import java.util.List;


public class StartFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Item>>,
        ItemsAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = "START_FRAGMENT";
    private final String URL = "https://www.sport.ru/rssfeeds/news.rss";

    private final int LOADER_FROM_DATABASE_ID = 1;
    private final int LOADER_FROM_NETWORK_ID = 2;

    private Loader<List<Item>> readingLoader;
    private List<Item> oldNews = null;

    private ItemsAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView infoText;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (ItemApplication.isNeedUpdate) {
            initData();
            readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_DATABASE_ID,
                    null, this);
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
            Log.d(TAG, "LOADER_FROM_DATABASE_ID");
            progressBar.setVisibility(View.INVISIBLE);
            infoText.setVisibility(View.INVISIBLE);

            if (data.size() > 0) {
                if (oldNews == null) {
                    oldNews = data;
                }
                updateRecycleView(data);
                oldNews = data;
            }

            if (ItemApplication.isNeedUpdate) {
                ItemApplication.isNeedUpdate = false;
                readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_NETWORK_ID, null, this);
                if (data.size() == 0) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            } else {
                if (data.size() == 0) {
                    infoText.setVisibility(View.VISIBLE);
                }
            }
        }
        else if (id == LOADER_FROM_NETWORK_ID) {
            Log.d(TAG, "LOADER_FROM_NETWORK_ID");
            if (RemoteApi.isOnline(getContext())) {
                ItemApplication.localApi.updateNews(data);
                readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_DATABASE_ID, null, this);
                readingLoader.forceLoad();
            } else {
                Snackbar snackbar = Snackbar.make(getView(), getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
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
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh1, R.color.refresh2, R.color.refresh3);
        swipeRefreshLayout.postDelayed(() -> {
            swipeRefreshLayout.setRefreshing(false);
            readingLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_FROM_NETWORK_ID, null, this);
            readingLoader.forceLoad();
        }, 3000);
    }


    private void initUI() {
        recyclerView = getActivity().findViewById(R.id.recycler_view_items);
        progressBar = getActivity().findViewById(R.id.progress_bar);
        infoText = getActivity().findViewById(R.id.info_text_view);
        swipeRefreshLayout = getActivity().findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    private void initData() {
        Log.d(TAG, "initData");
        adapter = new ItemsAdapter(getContext());
        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
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
