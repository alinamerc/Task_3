package com.zhirova.task_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zhirova.task_3.model.Rss;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchXML("https://www.sport.ru/rssfeeds/news.rss");
    }


    private void fetchXML(final String urlString) {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    List<Rss> allNews = parseXMLAndStoreIt(myparser);
                    stream.close();

                    for (int i = 0; i < allNews.size(); i++) {
                        Log.d(TAG, "=====================================================");
                        Log.d(TAG, "TITLE = " + allNews.get(i).getTitle() + "\n" +
                                "DESC = " + allNews.get(i).getDescription());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "ERROR", e);
                }
            }
        });
        thread.start();
    }


    public List<Rss> parseXMLAndStoreIt(XmlPullParser myParser) {
        List<Rss> allNews = new ArrayList<>();
        int event;
        String text = null;
        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                Rss curNews = new Rss("1",null, null, null);

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("title")){
                            curNews.setTitle(text);
                        }
                        else if(name.equals("link")){
                        }
                        else if(name.equals("description")){;
                            curNews.setDescription(text);
                        }
                        else{
                        }
                        break;
                }
                allNews.add(curNews);
                event = myParser.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return allNews;
    }


}
