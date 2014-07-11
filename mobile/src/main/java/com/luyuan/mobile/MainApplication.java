package com.luyuan.mobile;

import android.app.Application;

import com.luyuan.mobile.util.RequestManager;

import cn.jpush.android.api.JPushInterface;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        RequestManager.init(this);
        JPushInterface.init(this);
    }

}