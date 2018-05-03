package com.zhirova.task_3.xml_parser;

import android.util.Log;

import com.zhirova.task_3.model.NewsItem;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;


public class ItemXmlParser {

    public static List<NewsItem> parse(InputStream in) {
        List<NewsItem> news = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ItemHandler itemHandler = new ItemHandler();
            xmlReader.setContentHandler(itemHandler);
            xmlReader.parse(new InputSource(in));
            news = itemHandler.getNews();
        } catch (Exception e) {
            Log.e("XML", "ItemXmlParser: parse() failed", e);
        }
        return news;
    }


}
