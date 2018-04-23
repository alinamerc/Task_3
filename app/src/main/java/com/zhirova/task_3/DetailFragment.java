package com.zhirova.task_3;

import android.arch.lifecycle.Lifecycle;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhirova.task_3.application.ItemApplication;
import com.zhirova.task_3.database.DatabaseApi;
import com.zhirova.task_3.database.DatabaseHelper;
import com.zhirova.task_3.model.Item;

public class DetailFragment extends Fragment {

    private String itemId;
    private ImageView detailImage;
    private TextView titleText;
    private TextView descText;


    public static DetailFragment create(String itemId){
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.itemId = itemId;
        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initData();
    }


    private void initUI() {
        detailImage = getActivity().findViewById(R.id.detail_image_view);
        titleText = getActivity().findViewById(R.id.detail_title_text_view);
        descText = getActivity().findViewById(R.id.detail_desc_text_view);
    }


    private void initData() {
        Item curItem = ItemApplication.localApi.getSelectedNews(itemId);
        Picasso.get().load(curItem.getImage()).into(detailImage);
        titleText.setText(curItem.getTitle());
        descText.setText(curItem.getDescription());
    }


}
