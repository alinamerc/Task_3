package com.zhirova.task_3.network;


import android.util.Log;

import com.zhirova.task_3.model.Item;
import com.zhirova.task_3.xml_parser.ItemXmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class RemoteApi {

    private final String TAG = "REMOTE_API";


    public List<Item> loadNews(String urlString) {
        List<Item> items = new ArrayList<>();
        if (urlString != null) {
            InputStream resultStream = null;
            try {
                URL url = new URL(urlString);
                resultStream = downloadUrl(url);
                if (resultStream != null) {
                    items = ItemXmlParser.parse(resultStream);
                } else {
                    throw new IOException("No response received.");
                }
            } catch (IOException e) {
                Log.e(TAG, "MALFORMED URL ERROR", e);
            } finally {
                if (resultStream != null) {
                    try {
                        resultStream.close();
                    } catch (IOException e) {
                        Log.e(TAG, "INPUT STREAM CLOSING ERROR", e);
                    }
                }
            }
        }
        return items;
    }


    private boolean isConnectionPossible() {
//        if((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) {
//            new DownloadXmlTask().execute(URL);
//        }
//        else if ((sPref.equals(WIFI)) && (wifiConnected)) {
//            new DownloadXmlTask().execute(URL);
//        } else {
//            // show error
//        }
        return true;
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
