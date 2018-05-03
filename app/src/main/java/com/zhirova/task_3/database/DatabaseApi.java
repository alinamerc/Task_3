package com.zhirova.task_3.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zhirova.task_3.model.NewsItem;

import java.util.ArrayList;
import java.util.List;


public class DatabaseApi {

    private static final String TAG = "DATABASE_API";


    public static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME + " (" +
                ItemContract.ItemEntry.COLUMN_ID + " TEXT PRIMARY KEY NOT NULL," +
                ItemContract.ItemEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ItemContract.ItemEntry.COLUMN_DESC + " TEXT, " +
                ItemContract.ItemEntry.COLUMN_IMAGE + " TEXT, " +
                ItemContract.ItemEntry.COLUMN_DATE + " INTEGER NOT NULL);");
    }


    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE_NAME);
    }


    public static void addItem(String itemId, String itemTitle, String itemDesc,
                               String itemImage, int itemDate, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COLUMN_ID, itemId);
        values.put(ItemContract.ItemEntry.COLUMN_TITLE, itemTitle);
        values.put(ItemContract.ItemEntry.COLUMN_DESC, itemDesc);
        values.put(ItemContract.ItemEntry.COLUMN_IMAGE, itemImage);
        values.put(ItemContract.ItemEntry.COLUMN_DATE, itemDate);

        database.insert(ItemContract.ItemEntry.TABLE_NAME, null, values);
    }


    public static void deleteAllItems(SQLiteDatabase database) {
        database.delete(ItemContract.ItemEntry.TABLE_NAME, null, null);
    }


    public static List<NewsItem> getAllItems(SQLiteDatabase database) {
        Cursor cursor = database.query(ItemContract.ItemEntry.TABLE_NAME, ItemContract.ItemEntry.itemAllColumns,
                null, null, null, null,
                ItemContract.ItemEntry.COLUMN_DATE + " DESC");
        return getResultFromCursor(cursor);

    }


    public static NewsItem getSelectedItem(SQLiteDatabase database, String id) {
        String selection = ItemContract.ItemEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = database.query(ItemContract.ItemEntry.TABLE_NAME, ItemContract.ItemEntry.itemAllColumns,
                selection, selectionArgs, null, null, null);
        List<NewsItem> items = getResultFromCursor(cursor);
        return items.get(0);
    }


    private static List<NewsItem> getResultFromCursor(Cursor cursor) {
        List<NewsItem> result = new ArrayList<>();

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ID);
            int titleIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_TITLE);
            int descIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DESC);
            int imageIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_IMAGE);
            int dateIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE);

            while (cursor.moveToNext()) {
                String id = cursor.getString(idIndex);
                String title = cursor.getString(titleIndex);
                String desc = cursor.getString(descIndex);
                String image = cursor.getString(imageIndex);
                int date = cursor.getInt(dateIndex);

                NewsItem curItem = new NewsItem();
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
