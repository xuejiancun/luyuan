package com.luyuan.pad.mberp;

import android.app.Application;

import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.RequestManager;

public class MainApplication extends Application {

    public static boolean REMEMBER_IF_NEED_UPDATE = true;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        RequestManager.init(this);
        createImageCache();
    }

    private void createImageCache() {
        ImageCacheManager.getInstance().init(this, this.getPackageCodePath());
    }

}