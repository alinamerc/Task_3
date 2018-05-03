package com.zhirova.remote.mapper;

import android.util.Log;

import com.zhirova.domain.NewsItem;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ItemHandler extends DefaultHandler {

    private final String TAG = "ITEM_HANDLER";
    private ArrayList<NewsItem> news;
    private NewsItem curNewsItem;
    private StringBuilder builder;


    public ArrayList<NewsItem> getNews() {
        return news;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }


    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (curNewsItem != null){
            if (localName.equalsIgnoreCase("guid")) {
                curNewsItem.setId(builder.toString());
            }
            else if (localName.equalsIgnoreCase("title")) {
                curNewsItem.setTitle(builder.toString());
            }
            else if (localName.equalsIgnoreCase("description")) {
                String description = handleDescription(builder.toString());
                curNewsItem.setDescription(description);
            }
            else if (localName.equalsIgnoreCase("pubDate")) {
                try {
                    long date = handleDate(builder.toString());
                    curNewsItem.setDate(date);
                } catch (ParseException e) {
                    Log.e(TAG, "ERROR IN DATE-TIME PARSING!", e);
                }
            }
            else if (localName.equalsIgnoreCase("item")){
                news.add(curNewsItem);
            }
            builder.setLength(0);
        }
    }


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        news = new ArrayList<>();
        builder = new StringBuilder();
    }


    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase("item")){
            curNewsItem = new NewsItem();
            builder.setLength(0);
        }

        if (localName.equalsIgnoreCase("content")) {
            if (curNewsItem != null) {
                String image = attributes.getValue("url");
                curNewsItem.setImage(image);
            }
        }
    }


    private String handleDescription(String desc) {
        String newDesc1 = desc.replace("<div>", "");
        String newDesc2 = newDesc1.replace("</div>", "");
        String newDesc3 = newDesc2.replace("<br />", "");
        String newDesc4 = newDesc3.replace("&nbsp;", " ");
        return newDesc4;
    }


    private long handleDate(String dateString) throws ParseException {
        //2018-04-04T08:18:54+00:00
        // DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

        //Wed, 04 Apr 2018 12:08:21 +0000
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = format.parse(dateString);
        long millisecondsCount = date.getTime();
        return millisecondsCount;
    }


}





