package com.luyuan.mobile.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;

public class FileUtilities {

    /**
     * 获取文件路径<br />
     * eg. /data/data/com.example/files/
     *
     * @param mContext
     * @return
     */
    public static String getFileDir(Context mContext) {
        return mContext.getFilesDir().getPath() + File.separator;
    }

    /**
     * 获取缓存路径<br />
     * eg. <li>/data/data/com.example/cache</li> <li>
     * /sdcard/Android/data/com.example/cache</li>
     *
     * @param mContext 上下文对象
     * @return 缓存文件路径
     */
    public static String getCacheDir(Context mContext) {
        String cachePath = null;
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // 挂载了外部存储器
            cachePath = mContext.getExternalCacheDir() + File.separator;
        } else {
            cachePath = mContext.getCacheDir() + File.separator;
        }
        return cachePath;
    }

    /**
     * 获取文件的类型
     *
     * @param file 文件路径
     * @return
     */
    public static String getFileType(String file) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (inputStream == null) {
            return null;
        }
        byte[] buffer = new byte[2];
        // 文件类型代码
        String filecode = "";
        // 文件类型
        String fileType = "";
        // 通过读取出来的前两个字节来判断文件类型
        try {
            if (inputStream.read(buffer) != -1) {
                for (int i = 0; i < buffer.length; i++) {
                    // 获取每个字节与0xFF进行与运算来获取高位，这个读取出来的数据不是出现负数
                    // 并转换成字符串
                    filecode += Integer.toString((buffer[i] & 0xFF));
                }
                // 把字符串再转换成Integer进行类型判断
                switch (Integer.parseInt(filecode)) {
                    case 7790:
                        fileType = "exe";
                        break;
                    case 7784:
                        fileType = "midi";
                        break;
                    case 8297:
                        fileType = "rar";
                        break;
                    case 8075:
                        fileType = "zip";
                        break;
                    case 255216:
                        fileType = "jpg";
                        break;
                    case 7173:
                        fileType = "gif";
                        break;
                    case 6677:
                        fileType = "bmp";
                        break;
                    case 13780:
                        fileType = "png";
                        break;
                    default:
                        fileType = "unknown type: " + filecode;
                }
            }
        } catch (Exception e) {
            return fileType;
        }
        return fileType;
    }

    /**
     * 通过使用自带的文件管理器选中文件，解析它的路径
     *
     * @param mContext
     * @param uri
     * @return
     * @throws java.net.URISyntaxException
     */
    public static String getPath(Context mContext, Uri uri)
            throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = null;

            try {
                cursor = mContext.getContentResolver().query(uri, projection,
                        null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                if (cursor.moveToFirst()) {
                    String result = cursor.getString(column_index);
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * 根据文件判断该文件是否存在
     *
     * @param context
     * @param file
     * @return
     */
    public static boolean exists(Context context, String file) {
        return new File(file).exists();
    }

    /**
     * 检查文件是否过期 <br />
     * 若文件不存在，则直接返回true <br />
     * time指定过期的秒数
     *
     * @param context
     * @param file
     * @param time    单位：秒
     * @return
     */
    public static boolean expire(Context context, String file, int time) {
        if (!exists(context, file)) {
            return true;
        }
        File f = new File(file);
        if (Calendar.getInstance().getTimeInMillis() - f.lastModified() > time * 1000) {
            Log.d("file", "file expired");
            return true;
        }
        return false;
    }

    /**
     * 保存文件到包目录的files目录
     *
     * @param context
     * @param file
     * @param content
     * @throws java.io.IOException
     */
    public static void save(Context mContext, String file, String content)
            throws IOException {
        FileOutputStream outputStream = mContext.openFileOutput(file,
                Context.MODE_PRIVATE);
        outputStream.write(content.getBytes());
        outputStream.close();
    }
}
