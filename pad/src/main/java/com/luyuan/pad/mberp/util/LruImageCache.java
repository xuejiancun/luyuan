package com.luyuan.pad.mberp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LruImageCache implements ImageLoader.ImageCache {
    private static LruImageCache lruImageCache;
    LruCache<String, Bitmap> lruCache;
    DiskLruCache diskLruCache;
    final int RAM_CACHE_SIZE = 5 * 1024 * 1024;
    String DISK_CACHE_DIR = "image";
    final long DISK_MAX_SIZE = 20 * 1024 * 1024;

    public static LruImageCache getInstance(){
        if(lruImageCache == null){
            lruImageCache = new LruImageCache();
        }
        return lruImageCache;
    }

    private LruImageCache() {
        this.lruCache = new LruCache<String, Bitmap>(RAM_CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        File cacheDir = new File(Environment.getExternalStorageDirectory(), DISK_CACHE_DIR);
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        try {
            diskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bitmap getBitmap(String url) {
        String key = generateKey(url);
        Bitmap bmp = lruCache.get(key);
        if (bmp == null) {
            bmp = getBitmapFromDiskLruCache(key);
            //从磁盘读出后，放入内存
            if (bmp != null) {
                lruCache.put(key, bmp);
            }
        }
        //TODO  R.drawable.no_image & R.drawable.error_image
        return bmp;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        String key = generateKey(url);
        lruCache.put(url, bitmap);
        putBitmapToDiskLruCache(key, bitmap);
    }

    private void putBitmapToDiskLruCache(String key, Bitmap bitmap) {
        try {
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                editor.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromDiskLruCache(String key) {
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                if (inputStream != null) {
                    Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    return bmp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateKey(String url) {
        return MD5Util.encode(url);
    }
}
