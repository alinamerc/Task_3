package com.zhirova.local.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zhirova.domain.NewsItem;
import java.util.ArrayList;
import java.util.List;


public class DatabaseApi {

    private static final String TAG = "DATABASE_API";


    public static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + NewsItemContract.NewsItemEntry.TABLE_NAME + " (" +
                NewsItemContract.NewsItemEntry.COLUMN_ID + " TEXT PRIMARY KEY NOT NULL," +
                NewsItemContract.NewsItemEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                NewsItemContract.NewsItemEntry.COLUMN_DESC + " TEXT, " +
                NewsItemContract.NewsItemEntry.COLUMN_IMAGE + " TEXT, " +
                NewsItemContract.NewsItemEntry.COLUMN_DATE + " INTEGER NOT NULL);");
    }


    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS " + NewsItemContract.NewsItemEntry.TABLE_NAME);
    }


    public static void addItem(String itemId, String itemTitle, String itemDesc,
                               String itemImage, int itemDate, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(NewsItemContract.NewsItemEntry.COLUMN_ID, itemId);
        values.put(NewsItemContract.NewsItemEntry.COLUMN_TITLE, itemTitle);
        values.put(NewsItemContract.NewsItemEntry.COLUMN_DESC, itemDesc);
        values.put(NewsItemContract.NewsItemEntry.COLUMN_IMAGE, itemImage);
        values.put(NewsItemContract.NewsItemEntry.COLUMN_DATE, itemDate);

        database.insert(NewsItemContract.NewsItemEntry.TABLE_NAME, null, values);
    }


    public static void deleteAllItems(SQLiteDatabase database) {
        database.delete(NewsItemContract.NewsItemEntry.TABLE_NAME, null, null);
    }


    public static List<NewsItem> getAllItems(SQLiteDatabase database) {
        Cursor cursor = database.query(NewsItemContract.NewsItemEntry.TABLE_NAME, NewsItemContract.NewsItemEntry.newsItemAllColumns,
                null, null, null, null,
                NewsItemContract.NewsItemEntry.COLUMN_DATE + " DESC");
        return getResultFromCursor(cursor);

    }


    public static NewsItem getSelectedItem(SQLiteDatabase database, String id) {
        String selection = NewsItemContract.NewsItemEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = database.query(NewsItemContract.NewsItemEntry.TABLE_NAME, NewsItemContract.NewsItemEntry.newsItemAllColumns,
                selection, selectionArgs, null, null, null);
        List<NewsItem> items = getResultFromCursor(cursor);
        return items.get(0);
    }


    private static List<NewsItem> getResultFromCursor(Cursor cursor) {
        List<NewsItem> result = new ArrayList<>();

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(NewsItemContract.NewsItemEntry.COLUMN_ID);
            int titleIndex = cursor.getColumnIndex(NewsItemContract.NewsItemEntry.COLUMN_TITLE);
            int descIndex = cursor.getColumnIndex(NewsItemContract.NewsItemEntry.COLUMN_DESC);
            int imageIndex = cursor.getColumnIndex(NewsItemContract.NewsItemEntry.COLUMN_IMAGE);
            int dateIndex = cursor.getColumnIndex(NewsItemContract.NewsItemEntry.COLUMN_DATE);

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
