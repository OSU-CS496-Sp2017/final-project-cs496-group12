package com.cs496.clh.lowpolyfinalproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by soloh on 6/10/2017.
 */

public class LFSearchDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "imageSearch.db";
    private static final int DATABASE_VERSION = 1;

    public LFSearchDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_IMAGES_TABLE =
                "CREATE TABLE " + LFSearchContract.FavoriteImages.TABLE_NAME + " (" +
                        LFSearchContract.FavoriteImages._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        LFSearchContract.FavoriteImages.COLUMN_FULL_NAME + " TEXT NOT NULL, " +
                        LFSearchContract.FavoriteImages.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                        ");";
        db.execSQL(SQL_CREATE_FAVORITE_IMAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LFSearchContract.FavoriteImages.TABLE_NAME);
        onCreate(db);
    }
}
