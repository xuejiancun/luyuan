package com.luyuan.pad.mberp;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.ImageCacheManager.CacheType;
import com.luyuan.pad.mberp.util.RequestManager;

public class MainApplication extends Application {

    private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 80;
    private static int DISK_IMAGECACHE_QUALITY = 100;
    private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.JPEG;

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
        ImageCacheManager.getInstance().init(this,
                this.getPackageCodePath()
                , DISK_IMAGECACHE_SIZE
                , DISK_IMAGECACHE_COMPRESS_FORMAT
                , DISK_IMAGECACHE_QUALITY
                , CacheType.MEMORY);
    }

}