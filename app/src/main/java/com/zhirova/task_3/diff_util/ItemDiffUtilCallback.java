package com.zhirova.task_3.diff_util;


import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.zhirova.task_3.model.Item;

import java.util.List;


public class ItemDiffUtilCallback extends DiffUtil.Callback {

    private final List<Item> oldList;
    private final List<Item> newList;


    public ItemDiffUtilCallback(List<Item> oldList, List<Item> newList) {
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
        Item oldItem = oldList.get(oldItemPosition);
        Item newItem = newList.get(newItemPosition);
        return oldItem.getId().equals(newItem.getId());
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Item oldItem = oldList.get(oldItemPosition);
        Item newItem = newList.get(newItemPosition);
        return oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getDescription().equals(newItem.getDescription());
    }


}
