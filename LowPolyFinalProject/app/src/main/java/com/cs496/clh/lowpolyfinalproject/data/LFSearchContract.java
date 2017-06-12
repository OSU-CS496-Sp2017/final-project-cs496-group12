package com.cs496.clh.lowpolyfinalproject.data;

import android.provider.BaseColumns;
/**
 * Created by soloh on 6/10/2017.
 */

public class LFSearchContract {
    private LFSearchContract() {}

    public static class FavoriteImages implements BaseColumns {
        public static final String TABLE_NAME = "imageSearch";
        public static final String COLUMN_FULL_NAME = "path";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
