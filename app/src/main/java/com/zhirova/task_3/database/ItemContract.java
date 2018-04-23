package com.zhirova.task_3.database;


import android.provider.BaseColumns;


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
