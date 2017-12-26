package com.dkl.auser.yvtc1212exam;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.apache.http.conn.ConnectTimeoutException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by auser on 2017/12/12.
 */


public class MyHandler extends DefaultHandler {
    boolean isTitle = false;
    boolean isItem = false;
    public ArrayList<String> mylist = new ArrayList<>();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("title"))
        {
            isTitle = true;
        }
        if (qName.equals("item"))
        {
            isItem = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("title"))
        {
            isTitle = false;
        }
        if (qName.equals("item"))
        {
            isItem = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (isTitle && isItem)
        {
            Log.d("NET", new String(ch, start, length));
            mylist.add(new String(ch, start, length));
        }
    }
    public void parseRss(String uri)
            throws ParserConfigurationException, SAXException, IOException, ConnectTimeoutException, UnknownHostException
    {
        Object localObject = SAXParserFactory.newInstance().newSAXParser();
        uri = (HttpURLConnection)new URL(uri).openConnection();
//        paramString.setConnectTimeout(5000);
//        paramString.setReadTimeout(5000);
        localObject = ((SAXParser)localObject).getXMLReader();
//        paramString = new InputSource(paramString.getInputStream());
        ((XMLReader)localObject).setContentHandler(this);
        ((XMLReader)localObject).parse(uri);
    }

    public class NewsAdapter
            extends ArrayAdapter<RssNews>
    {
        private List<RssNews> list;

        public NewsAdapter(int paramInt, List<RssNews> paramList)
        {
            super(paramList);
            Object localObject;
            this.list = localObject;
        }

        public int getCount()
        {
            return this.list.size();
        }

        public RssNews getItem(int paramInt)
        {
            return (RssNews)this.list.get(paramInt);
        }

        public long getItemId(int paramInt)
        {
            return paramInt;
        }

        public List<RssNews> getRssNewslist()
        {
            return this.list;
        }
    }

    public class RssNews
    {
        public String description;
        public String link;
        public String pubDate;
        public String title;

        public RssNews() {}
    }
}
