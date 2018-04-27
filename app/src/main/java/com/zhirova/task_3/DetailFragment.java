package com.zhirova.task_3;

import android.arch.lifecycle.Lifecycle;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private static final String BUNDLE_ID = "ITEM_ID";
    private ImageView detailImage;
    private TextView titleText;
    private TextView descText;


    public static DetailFragment create(String id){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initData();
    }


    private void initUI(View root) {
        detailImage = root.findViewById(R.id.detail_image_view);
        titleText = root.findViewById(R.id.detail_title_text_view);
        descText = root.findViewById(R.id.detail_desc_text_view);
    }


    private void initData() {
        Bundle bundle = getArguments();
        String curItemId = bundle.getString(BUNDLE_ID);
        Item curItem = ItemApplication.localApi.getSelectedNews(curItemId);

        Picasso.get().load(curItem.getImage()).into(detailImage);
        titleText.setText(curItem.getTitle());
        descText.setText(curItem.getDescription());
    }


    public String getShownId() {
        Bundle bundle = getArguments();
        String curItemId = bundle.getString(BUNDLE_ID);
        return curItemId;
    }


    public void update(String curItemId) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, curItemId);
        setArguments(bundle);
        initData();
    }


}
