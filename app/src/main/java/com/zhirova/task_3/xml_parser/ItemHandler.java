package com.zhirova.task_3.xml_parser;

import android.util.Log;

import com.zhirova.task_3.model.Item;

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
    private ArrayList<Item> news;
    private Item curItem;
    private StringBuilder builder;
    private boolean isFirstTitle;


    public ArrayList<Item> getNews() {
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
        if (curItem != null){
            if (localName.equalsIgnoreCase("title")) {
                curItem.setTitle(builder.toString());
            }
            else if (localName.equalsIgnoreCase("description")) {
                String description = handleDescription(builder.toString());
                curItem.setDescription(description);
            }
            else if (localName.equalsIgnoreCase("pubDate")) {
                try {
                    long date = handleDate(builder.toString());
                    curItem.setDate(date);
                } catch (ParseException e) {
                    Log.e(TAG, "ERROR IN DATE-TIME PARSING!", e);
                }
            }
            else if (localName.equalsIgnoreCase("item")){
                news.add(curItem);
            }
            builder.setLength(0);
        }
        else if (isFirstTitle && localName.equalsIgnoreCase("title")) {
            isFirstTitle = false;
            Item specialItem = new Item();
            specialItem.setTitle(builder.toString());
            news.add(specialItem);
            builder.setLength(0);
        }
    }


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        news = new ArrayList<>();
        builder = new StringBuilder();
        isFirstTitle = true;
    }


    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase("item")){
            curItem = new Item();
            builder.setLength(0);
        }

        if (localName.equalsIgnoreCase("content")) {
            if (curItem != null) {
                String image = attributes.getValue("url");
                curItem.setImage(image);
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





