package com.zhirova.presentation.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhirova.presentation.R;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
