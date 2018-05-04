package com.zhirova.common;


import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;


public class NewsItemApplication extends Application implements LifecycleObserver {

    private final String TAG = "ITEM_APPLICATION";
    private static NewsItemApplication INSTANCE;
    public static boolean isNeedUpdate = false;


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onEnterForeground() {
        isNeedUpdate = true;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onEnterBackground() {
    }


    public static Context getContext(){
        return INSTANCE;
    }


}
