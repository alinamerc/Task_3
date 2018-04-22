package com.zhirova.task_3;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


// https://devcolibri.com/android-fragment-%D1%87%D1%82%D0%BE-%D1%8D%D1%82%D0%BE/
// https://metanit.com/java/android/8.2.php
// https://metanit.com/java/android/8.5.php


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN_ACTIVITY";
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            StartFragment curFragment = new StartFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, curFragment);
            fragmentTransaction.commit();
        }
    }


}
