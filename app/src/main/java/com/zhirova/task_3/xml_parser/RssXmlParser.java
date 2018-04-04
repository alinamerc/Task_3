package com.zhirova.task_3.xml_parser;

import android.util.Log;

import com.zhirova.task_3.model.Rss;
import com.zhirova.task_3.xml_parser.RssHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;


public class RssXmlParser {

    public static List<Rss> parse(InputStream in) {
        List<Rss> news = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            RssHandler rssHandler = new RssHandler();
            xmlReader.setContentHandler(rssHandler);
            xmlReader.parse(new InputSource(in));
            news = rssHandler.getNews();
        } catch (Exception e) {
            Log.e("XML", "SAXXMLParser: parse() failed", e);
        }
        return news;
    }


}
