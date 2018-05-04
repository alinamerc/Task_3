package com.zhirova.presentation.diff_util;


import android.support.v7.util.DiffUtil;
import com.zhirova.presentation.model.NewsItemPresent;

import java.util.List;


public class ItemDiffUtilCallback extends DiffUtil.Callback {

    private final List<NewsItemPresent> oldList;
    private final List<NewsItemPresent> newList;


    public ItemDiffUtilCallback(List<NewsItemPresent> oldList, List<NewsItemPresent> newList) {
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
        NewsItemPresent oldItem = oldList.get(oldItemPosition);
        NewsItemPresent newItem = newList.get(newItemPosition);

        return oldItem.getId().equals(newItem.getId());
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        NewsItemPresent oldItem = oldList.get(oldItemPosition);
        NewsItemPresent newItem = newList.get(newItemPosition);

        return oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getDescription().equals(newItem.getDescription());
    }


}
