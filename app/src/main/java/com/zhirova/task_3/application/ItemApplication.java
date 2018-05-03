package com.zhirova.task_3.application;


import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;

import com.zhirova.task_3.data_repository.LocalApi;


public class ItemApplication extends Application implements LifecycleObserver {

    private final String TAG = "ITEM_APPLICATION";
    private static LocalApi localApi;
    public static boolean isNeedUpdate = false;
    private static ItemApplication INSTANCE;


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        localApi = LocalApi.getInstance(getBaseContext());
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onEnterForeground() {
        isNeedUpdate = true;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onEnterBackground() {
    }

    public static LocalApi getLocalApi(){
        return localApi;
    }

    public static Context getContext(){
        return INSTANCE;
    }
}
