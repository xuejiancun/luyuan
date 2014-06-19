package com.luyuan.pad.mberp.util;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class ImageCacheManager {

    private static ImageCacheManager mInstance;
    private ImageLoader mLargeImageLoader;
    private ImageLoader mSmallImageLoader;

    private ImageCache mLargeImageCache;
    private ImageCache mSmallImageCache;

    public static ImageCacheManager getInstance() {
        if (mInstance == null)
            mInstance = new ImageCacheManager();

        return mInstance;
    }

    public void init(Context context, String uniqueName) {
        mLargeImageCache = new LruImageCache(context, uniqueName);
        mSmallImageCache = new BitmapLruImageCache();
        mLargeImageLoader = new ImageLoader(RequestManager.getRequestQueue(), mLargeImageCache);
        mSmallImageLoader = new ImageLoader(RequestManager.getRequestQueue(), mSmallImageCache);
    }

    public ImageLoader getLargeImageLoader() {
        return mLargeImageLoader;
    }

    public ImageLoader getSmallImageLoader() {
        return mSmallImageLoader;
    }

}

