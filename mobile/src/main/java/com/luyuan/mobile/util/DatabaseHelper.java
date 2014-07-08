package com.luyuan.mobile.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.luyuan.mobile.model.Shortcut;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "luyuan.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper helper;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (helper == null) {
            helper = new DatabaseHelper(context);
        }

        return helper;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createShortcutTable =
                "CREATE TABLE `shortcut` " +
                        "(" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "`code` VARCHAR, " +
                        "`name` VARCHAR " +
                        ") ";
        sqLiteDatabase.execSQL(createShortcutTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table shortcut");
        onCreate(sqLiteDatabase);
    }

    public void createShortcut(String code, String name) {
        final SQLiteDatabase writableDatabase = getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        contentValues.put("code", code);
        contentValues.put("name", name);

        writableDatabase.insertOrThrow("shortcut", null, contentValues);
    }

    public void removeShortcut(String code) {
        final SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("shortcut", "code = ?", new String[]{code});
    }

    public void removeShortcuts() {
        getWritableDatabase().execSQL("delete from shortcut");
    }

    public List<Shortcut> loadShortcuts() {
        final SQLiteDatabase readableDatabase = getReadableDatabase();

        List<Shortcut> shortcuts;
        final Cursor shortcutCursor = readableDatabase.rawQuery("select id, code, name from shortcut order by id desc", null);

        try {
            shortcuts = new ArrayList<Shortcut>();

            while (shortcutCursor.moveToNext()) {
                final Shortcut shortcut = shortcutFromCursor(shortcutCursor);

                shortcuts.add(shortcut);
            }
        } finally {
            shortcutCursor.close();
        }

        return shortcuts;
    }

    private Shortcut shortcutFromCursor(Cursor cursor) {
        final Shortcut shortcut = new Shortcut(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        return shortcut;
    }


}
