package com.zhirova.presentation.diff_util;


import android.support.v7.util.DiffUtil;

import com.zhirova.domain.NewsItem;
import java.util.List;


public class ItemDiffUtilCallback extends DiffUtil.Callback {

    private final List<NewsItem> oldList;
    private final List<NewsItem> newList;


    public ItemDiffUtilCallback(List<NewsItem> oldList, List<NewsItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }


    @Override
    public int getOldListSize() {
        return oldList.size();
    }


    @Override
    public int getNewListSize() {
        return newList.size();
    }


    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        NewsItem oldItem = oldList.get(oldItemPosition);
        NewsItem newItem = newList.get(newItemPosition);

        return oldItem.getId().equals(newItem.getId());
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        NewsItem oldItem = oldList.get(oldItemPosition);
        NewsItem newItem = newList.get(newItemPosition);

        return oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getDescription().equals(newItem.getDescription());
    }


}
