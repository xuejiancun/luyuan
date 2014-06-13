package com.luyuan.pad.mberp.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

public class LruImageCache implements ImageLoader.ImageCache {

    private static int RAM_IMAGECACHE_SIZE = 1024 * 1024 * 40;
    private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 80;
    private static int DISK_IMAGECACHE_QUALITY = 100;
    private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;

    private BitmapLruImageCache bitmapLruImageCache;
    private DiskLruImageCache diskLruImageCache;

    public LruImageCache(Context context, String uniqueName) {
        bitmapLruImageCache = new BitmapLruImageCache(RAM_IMAGECACHE_SIZE);
        diskLruImageCache = new DiskLruImageCache(context, uniqueName, DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT, DISK_IMAGECACHE_QUALITY);
    }

    @Override
    public Bitmap getBitmap(String url) {
        String key = createKey(url);
        Bitmap bmp = bitmapLruImageCache.get(key);
        if (bmp == null) {
            bmp = diskLruImageCache.getBitmap(key);
            // Put it into RAM
            if (bmp != null) {
                bitmapLruImageCache.put(key, bmp);
            }
        }
        return bmp;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        String key = createKey(url);
        bitmapLruImageCache.put(key, bitmap);
        diskLruImageCache.putBitmap(key, bitmap);
    }

    private String createKey(String url) {
        return String.valueOf(url.hashCode());
    }

}
