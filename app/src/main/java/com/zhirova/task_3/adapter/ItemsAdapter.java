package com.zhirova.task_3.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhirova.task_3.R;
import com.zhirova.task_3.model.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private final LayoutInflater inflater;
    private List<Item> items = new ArrayList<>();
    private ClickListener clickListener;


    public ItemsAdapter (Context context) {
        this.inflater = LayoutInflater.from(context);
    }


    public void setData(List<Item> items){
        this.items.clear();
        if (items != null){
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_list_item, parent, false);
        ItemsViewHolder holder = new ItemsViewHolder(view);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null){
                clickListener.onClick((Item)v.getTag());
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Item curItem = items.get(position);
        holder.itemView.setTag(curItem);
        holder.itemTitle.setText(curItem.getTitle());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    static class ItemsViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTitle;

        ItemsViewHolder(View view){
            super(view);
            itemTitle = view.findViewById(R.id.item_list_text_view);
        }
    }


    public interface ClickListener{
        void onClick(Item item);
    }


}
