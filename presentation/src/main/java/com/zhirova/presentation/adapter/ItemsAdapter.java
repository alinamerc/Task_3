package com.zhirova.presentation.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.zhirova.presentation.R;
import com.zhirova.presentation.model.NewsItemPresent;

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private final LayoutInflater inflater;
    private List<NewsItemPresent> items = new ArrayList<>();
    private ClickListener clickListener;
    private String selectId = null;


    public ItemsAdapter(Context context, String selectId) {
        this.inflater = LayoutInflater.from(context);
        this.selectId = selectId;
    }


    public void setData(List<NewsItemPresent> items){
        this.items.clear();
        if (items != null){
            this.items.addAll(items);
        }
        //notifyDataSetChanged();
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }


    public int positionById(String id){
        for(int i = 0; i < items.size(); i++){
            if (items.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_list_item, parent, false);
        ItemsViewHolder holder = new ItemsViewHolder(view);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                String itemId = (String)v.getTag();
                clickListener.onClick(itemId);
                selectId = itemId;
                notifyDataSetChanged();
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        NewsItemPresent curItem = items.get(position);
        holder.itemView.setTag(curItem.getId());
        holder.itemTitle.setText(curItem.getTitle());

        if (curItem.getId().equals(selectId)) {
            holder.itemElement.setBackgroundResource(R.color.backColorPressed);
        } else {
            holder.itemElement.setBackgroundResource(R.color.backColorDefault);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        FrameLayout itemElement;

        ItemsViewHolder(View view){
            super(view);
            itemTitle = view.findViewById(R.id.item_list_text_view);
            itemElement = view.findViewById(R.id.category_item);
        }
    }


    public interface ClickListener{
        void onClick(String itemId);
    }


}
