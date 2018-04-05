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

    public static abstract class ItemEntry implements BaseColumns {
        public final static String TABLE_NAME = "Notes";
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


    private static final String TAG = "ITEM_CONTRACT";

    private ItemContract() {}


    public static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + ItemEntry.TABLE_NAME + " (" +
                ItemEntry.COLUMN_ID + " TEXT PRIMARY KEY NOT NULL," +
                ItemEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ItemEntry.COLUMN_DESC + " TEXT, " +
                ItemEntry.COLUMN_IMAGE + " TEXT, " +
                ItemEntry.COLUMN_DATE + " INTEGER NOT NULL);");
    }


    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME);
    }


    public static void addItem(String itemId, String itemTitle, String itemDesc,
                               String itemImage, String itemDate, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ID, itemId);
        values.put(ItemEntry.COLUMN_TITLE, itemTitle);
        values.put(ItemEntry.COLUMN_DESC, itemDesc);
        values.put(ItemEntry.COLUMN_IMAGE, itemImage);
        values.put(ItemEntry.COLUMN_DATE, itemDate);

        database.insert(ItemEntry.TABLE_NAME, null, values);
    }


    public static void deleteAll(SQLiteDatabase database) {
        database.delete(ItemEntry.TABLE_NAME, null, null);
    }


    public static List<Item> getAllItems(SQLiteDatabase database) {
        Cursor cursor = database.query(ItemEntry.TABLE_NAME, ItemEntry.itemAllColumns, null,
                null, null, null, null);
        return getResultFromCursor(cursor);
    }


    private static List<Item> getResultFromCursor(Cursor cursor) {
        List<Item> result = new ArrayList<>();

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ID);
            int titleIndex = cursor.getColumnIndex(ItemEntry.COLUMN_TITLE);
            int descIndex = cursor.getColumnIndex(ItemEntry.COLUMN_DESC);
            int imageIndex = cursor.getColumnIndex(ItemEntry.COLUMN_IMAGE);
            int dateIndex = cursor.getColumnIndex(ItemEntry.COLUMN_DATE);

            while (cursor.moveToNext()) {
                String id = cursor.getString(idIndex);
                String title = cursor.getString(titleIndex);
                String desc = cursor.getString(descIndex);
                String image = cursor.getString(imageIndex);
                int date = cursor.getInt(dateIndex);

                Item curItem = new Item();
                curItem.setId(id);
                curItem.setTitle(title);
                curItem.setDescription(desc);
                curItem.setImage(image);
                curItem.setDate(date);

                result.add(curItem);
            }
            try{
                cursor.close();
            } catch (Exception e){
                Log.e(TAG, "DATABASE CURSOR CLOSE ERROR", e);
            }
        }
        return result;
    }


}
