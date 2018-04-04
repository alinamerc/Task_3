package com.zhirova.task_3.xml_parser;

import android.util.Log;

import com.zhirova.task_3.model.Rss;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.jar.Attributes;


public class RssHandler extends DefaultHandler {

    private ArrayList<Rss> news;
    private Rss curNews;
    private StringBuilder builder;


    public ArrayList<Rss> getNews() {
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
        if (curNews != null){
            if (localName.equalsIgnoreCase("title")) {
                curNews.setTitle(builder.toString());
            }
            else if (localName.equalsIgnoreCase("description")) {
                curNews.setDescription(builder.toString());
            }
            else if (localName.equalsIgnoreCase("content")) {
                curNews.setImage(builder.toString());
            }
            else if (localName.equalsIgnoreCase("pubDate")) {
                curNews.setDate(builder.toString());
            }
            else if (localName.equalsIgnoreCase("item")){
                news.add(curNews);
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
            curNews = new Rss();
            builder.setLength(0);
        }
    }


}
