package com.zhirova.task_3.model;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class DownloadXmlTask extends AsyncTask<String, Void, List<Rss>> {

    private final String TAG = "DOWNLOAD_XML_TASK";


    @Override
    protected List<Rss> doInBackground(String... urls) {
        if (!isCancelled() && urls != null && urls.length > 0) {
            String urlString = urls[0];
            try {
                URL url = new URL(urlString);
                InputStream result = downloadUrl(url);
                if (result != null) {

                } else {
                    throw new IOException("No response received.");
                }
            } catch(IOException e) {
                Log.e(TAG, "ERROR", e);
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(List<Rss> result) {

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
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return stream;
    }


}










