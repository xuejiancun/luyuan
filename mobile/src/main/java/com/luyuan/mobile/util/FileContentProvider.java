package com.luyuan.mobile.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileContentProvider extends ContentProvider {

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) {

        ParcelFileDescriptor parcel = null;

        String fileNameRequested = uri.getLastPathSegment();
        String[] name = fileNameRequested.split("\\.");
        String prefix = name[0];
        String suffix = name[1];

        if (suffix.equalsIgnoreCase("js")) {
            InputStream is = null;
            try {
                is = getContext().getAssets().open("jstest.js");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            File file = stream2file(is, prefix, suffix);
            try {
                parcel = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            } catch (FileNotFoundException e) {
                Log.e("FileContentProvider", "uri " + uri.toString(), e);
            }
        }
        return parcel;
    }

    public File stream2file(InputStream in, String prefix, String suffix) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile(prefix, suffix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile;
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int delete(Uri uri, String s, String[] as) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentvalues) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    @Override
    public Cursor query(Uri uri, String[] as, String s, String[] as1, String s1) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    @Override
    public int update(Uri uri, ContentValues contentvalues, String s, String[] as) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }


}