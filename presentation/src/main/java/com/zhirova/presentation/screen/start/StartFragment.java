package com.zhirova.presentation.screen.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.zhirova.domain.NewsItem;
import com.zhirova.presentation.R;
import com.zhirova.presentation.adapter.ItemsAdapter;
import com.zhirova.presentation.application.NewsItemApplication;
import com.zhirova.presentation.diff_util.ItemDiffUtilCallback;
import com.zhirova.presentation.model.NewsItemPresent;

import java.util.ArrayList;
import java.util.List;


public class StartFragment extends Fragment
        implements StartContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        ItemsAdapter.ClickListener {

    private final String TAG = "START_FRAGMENT";
    private final String BUNDLE_SELECTED = "SELECTED_ITEM";

    private StartContract.Presenter startPresenter;
    private List<NewsItemPresent> oldNews = new ArrayList<>();

    private ItemsAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView infoText;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startPresenter = new StartPresenter();
        initUI(view);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        super.onStart();
        startPresenter.subscribe(getContext(), NewsItemApplication.needUpdate, this);
        NewsItemApplication.needUpdate = false;
    }


    @Override
    public void onStop() {
        super.onStop();
        startPresenter.unsubsribe(this);
    }


    @Override
    public void updateNewsList(List<NewsItemPresent> actualNews) {
        List<NewsItemPresent> oldList = new ArrayList<>(oldNews);
        List<NewsItemPresent> newList = new ArrayList<>(actualNews);
        oldNews = new ArrayList<>(actualNews);

        ItemDiffUtilCallback itemDiffUtilCallback = new ItemDiffUtilCallback(oldList, newList);
        DiffUtil.DiffResult itemDiffResult = DiffUtil.calculateDiff(itemDiffUtilCallback, true);
        initAdapter();
        adapter.setData(actualNews);
        itemDiffResult.dispatchUpdatesTo(adapter);

//        View detailsFrame = getActivity().findViewById(R.id.details);
//        isDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
//
//        if (isDualPane) {
//            if (selectedItemId == null) {
//                selectedItemId = actualNews.get(0).getId();
//            }
//            onClick(selectedItemId);
//            adapter.setSelectId(selectedItemId);
//            if (ItemApplication.isNeedUpdate) {
//                selectedItemId = null;
//            }
//        }
//
//        int selectedPosition = 0;
//        if (selectedItemId != null) {
//            selectedPosition = adapter.positionById(selectedItemId);
//        }
//        int finalSelectedPosition = selectedPosition;
//        Handler scrollHandler = new Handler(Looper.getMainLooper());
//        scrollHandler.postDelayed(() -> {
//            recyclerView.smoothScrollToPosition(finalSelectedPosition);
//        }, 200);
    }


    @Override
    public void updateMessagesAndEnvironment(String status) {
        progressBar.setVisibility(View.GONE);
        infoText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        if (status.equals("EMPTY_DB")) {
            progressBar.setVisibility(View.VISIBLE);
        } else if (status.equals("NO_NEWS")) {
            infoText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else if (status.equals("NO_CONNECTION")) {
            Snackbar snackbar = Snackbar.make(getView(), getResources().getString(R.string.no_internet),
                    Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh1, R.color.refresh2, R.color.refresh3);

        swipeRefreshLayout.postDelayed(() -> {
            swipeRefreshLayout.setRefreshing(false);
            startPresenter.refreshNews();
        }, 1000);
    }


    @Override
    public void onClick(String itemId) {
    }


    private void initUI(View root) {
        recyclerView = root.findViewById(R.id.recycler_view_items);
        progressBar = root.findViewById(R.id.progress_bar);
        infoText = root.findViewById(R.id.info_text_view);
        swipeRefreshLayout = root.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    private void initAdapter() {
        adapter = new ItemsAdapter(getContext(), null);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }


    private void print(List<NewsItem> news) {
        for (int i = 0; i < news.size(); i++) {
            Log.d(TAG, "==========================================");
            Log.d(TAG, "ID = " + news.get(i).getId() + "\n" +
                    "TITLE = " + news.get(i).getTitle() + "\n" +
                    "IMAGE = " + news.get(i).getImage() + "\n" +
                    "DATE = " + news.get(i).getDate());
        }
    }


}
