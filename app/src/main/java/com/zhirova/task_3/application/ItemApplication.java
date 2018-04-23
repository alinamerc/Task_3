package com.zhirova.task_3.application;


import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;

import com.zhirova.task_3.data_repository.LocalApi;


public class ItemApplication extends Application implements LifecycleObserver {

    private final String TAG = "ITEM_APPLICATION";
    public static LocalApi localApi;
    public static boolean isNeedUpdate = false;


    @Override
    public void onCreate() {
        super.onCreate();
        localApi = LocalApi.getInstance(getBaseContext());
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        //Log.d(TAG, "onCreate");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onEnterForeground() {
        //Log.d(TAG, "onEnterForeground");
        isNeedUpdate = true;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onEnterBackground() {
        //Log.d(TAG, "onEnterBackground");
    }


}
