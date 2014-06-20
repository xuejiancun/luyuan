package com.luyuan.mobile;

import android.app.Application;

import com.luyuan.mobile.util.RequestManager;

public class MainApplication extends Application {

    public static boolean REMEMBER_IF_NEED_UPDATE = true;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        RequestManager.init(this);
    }

}