package com.zhirova.task_3.data_reading;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.zhirova.task_3.database.DatabaseHelper;
import com.zhirova.task_3.database.ItemContract;
import com.zhirova.task_3.model.Item;

import java.util.List;


public class DownloadDatabaseTask extends AsyncTask<String, Void, List<Item>> {

    private final Context context;


    public DownloadDatabaseTask(Context context) {
        this.context = context;
    }


    @Override
    protected List<Item> doInBackground(String... strings) {
        SQLiteDatabase database = new DatabaseHelper(context).getWritableDatabase();
        return ItemContract.getAllItems(database);
    }


    @Override
    protected void onPostExecute(List<Item> result) {
    }


}
