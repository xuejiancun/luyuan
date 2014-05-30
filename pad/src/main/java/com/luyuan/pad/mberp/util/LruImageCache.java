package com.luyuan.pad.mberp.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

public class LruImageCache implements ImageLoader.ImageCache {

    BitmapLruImageCache bitmapLruImageCache;
    DiskLruImageCache diskLruImageCache;

    public LruImageCache(Context context, String uniqueName) {
        bitmapLruImageCache = new BitmapLruImageCache();
        diskLruImageCache = new DiskLruImageCache(context, uniqueName);
    }

    private String createKey(String url) {
        return String.valueOf(url.hashCode());
    }

    @Override
    public Bitmap getBitmap(String url) {
        String key = createKey(url);
        Bitmap bmp = bitmapLruImageCache.get(key);
        if (bmp == null) {
            bmp = diskLruImageCache.getBitmap(key);
            // Put it in RAM
            if (bmp != null) {
                bitmapLruImageCache.put(key, bmp);
            }
        }
        //TODO  R.drawable.no_image & R.drawable.error_image
        return bmp;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        String key = createKey(url);
        bitmapLruImageCache.put(key, bitmap);
        diskLruImageCache.putBitmap(key, bitmap);
    }

}
