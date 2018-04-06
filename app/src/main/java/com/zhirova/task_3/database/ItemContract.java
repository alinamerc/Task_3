package com.zhirova.task_3.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.zhirova.task_3.model.Item;

import java.util.ArrayList;
import java.util.List;


public final class ItemContract {

    private ItemContract() {}


    public static abstract class ItemEntry implements BaseColumns {
        public final static String TABLE_NAME = "News";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DATE = "date";

        public static final String[] itemAllColumns = {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_DESC,
                COLUMN_IMAGE,
                COLUMN_DATE
        };
    }


}
