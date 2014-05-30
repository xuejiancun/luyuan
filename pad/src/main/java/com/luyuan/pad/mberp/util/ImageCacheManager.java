package com.luyuan.pad.mberp.util;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class ImageCacheManager {

    private static ImageCacheManager mInstance;

    private ImageLoader mImageLoader;

    private ImageCache mLruImageCache;

    public static ImageCacheManager getInstance() {
        if (mInstance == null)
            mInstance = new ImageCacheManager();

        return mInstance;
    }

    public void init(Context context, String uniqueName) {
        mLruImageCache = new LruImageCache(context, uniqueName);
        mImageLoader = new ImageLoader(RequestManager.getRequestQueue(), mLruImageCache);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}

