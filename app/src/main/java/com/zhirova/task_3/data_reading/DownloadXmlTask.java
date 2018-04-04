package com.zhirova.task_3.data_reading;


import android.os.AsyncTask;
import android.util.Log;

import com.zhirova.task_3.StartFragment;
import com.zhirova.task_3.model.Item;
import com.zhirova.task_3.xml_parser.ItemXmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class DownloadXmlTask extends AsyncTask<String, Void, List<Item>> {

    private final String TAG = "DOWNLOAD_XML_TASK";
    private final StartFragment fragment;


    public DownloadXmlTask(StartFragment fragment) {
        this.fragment = fragment;
    }


    @Override
    protected List<Item> doInBackground(String... urls) {
        List<Item> items = new ArrayList<>();
        if (!isCancelled() && urls != null && urls.length > 0) {
            String urlString = urls[0];
            InputStream resultStream = null;
            try {
                URL url = new URL(urlString);
                resultStream = downloadUrl(url);
                if (resultStream != null) {
                    items = ItemXmlParser.parse(resultStream);
                } else {
                    throw new IOException("No response received.");
                }
            } catch(IOException e) {
                Log.e(TAG, "ERROR", e);
            } finally {
                if (resultStream != null) {
                    try {
                        resultStream.close();
                    } catch (IOException e) {
                        Log.e(TAG, "ERROR", e);
                    }
                }
            }
        }
        return items;
    }


    @Override
    protected void onPostExecute(List<Item> result) {
        fragment.dataBinding(result);
    }


    private InputStream downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            stream = connection.getInputStream();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return stream;
    }


}










