package com.zhirova.task_3.data_reading;


import android.util.Log;
import android.util.Xml;

import com.zhirova.task_3.model.Rss;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class RssXmlParser {

    private final String TAG = "RSS_XML_PARSER";
    private static final String ns = null;


    public List<Rss> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }


    private List<Rss> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Rss> news = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("item")) {
                news.add(readItem(parser));
            } else {
                //skip(parser);
            }
        }
        return news;
    }


    private Rss readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        //Log.d(TAG, "readItem");
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String image = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("description")) {
                description = readDescriprion(parser);
            } else if (name.equals("media:content url")) {
                image = readImage(parser);
            } else {
                skip(parser);
            }
        }
        Log.d(TAG, "============================================");
        Log.d(TAG, "title = " + title);
        Log.d(TAG, "description = " + description);
        Log.d(TAG, "image = " + image);
        return new Rss(UUID.randomUUID().toString(), title, description, image);
    }


    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }


    private String readDescriprion(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }


    private String readImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "media:content url");
        String image = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "media:content url");
        return image;
    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
















