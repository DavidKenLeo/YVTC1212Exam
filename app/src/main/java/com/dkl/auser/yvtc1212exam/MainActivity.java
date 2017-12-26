package com.dkl.auser.yvtc1212exam;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.dkl.auser.yvtc1212exam.test.GetNews;
//import com.dkl.auser.yvtc1212exam.test.ListContent;
//import com.dkl.auser.yvtc1212exam.test.ListSite;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {
    private long rowId;
    private ListView siteList;
    private SimpleCursorAdapter siteCursorAdapter;
//    private WebView wv;
    private MyHandler.NewsAdapter contentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        startListen();
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new UTF8StringRequest("https://udn.com/rssfeed/news/2/6638?ch=news",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final MyHandler dataHandler = new MyHandler();
                        SAXParserFactory spf = SAXParserFactory.newInstance();
                        SAXParser sp = null;
                        try {
                            sp = spf.newSAXParser();
                            XMLReader xr = sp.getXMLReader();
                            xr.setContentHandler(dataHandler);
                            xr.parse(new InputSource(new StringReader(response)));
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    MainActivity.this,
                                    R.layout.list_site_item,
                                    dataHandler.mylist
                            );
                            siteList.setAdapter(adapter);
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        queue.start();
    }
//        this.siteCursorAdapter = new ListSite.SiteCursorAdapter(this, R.layout.list_site_item, this.rssDbAdapter.getAllSite(this.rowId)
//                , new String[] { R.id.textView }, new String[] { R.id.textView2 }, new int[] { R.id.image }
//        );
//        this.siteList.setAdapter(this.siteCursorAdapter);



    ////////////////////
    public void startListen()
    {
        this.siteList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
//            public String site_url = "https://www.udn.com/rssfeed/news/2/6638?ch=news";

            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
            {

                paramAnonymousAdapterView = new Intent("android.intent.action.VIEW", Uri.parse(((MyHandler.RssNews)MainActivity.this.contentAdapter.getRssNewslist().get(paramAnonymousInt)).link));
                MainActivity.this.startActivity(paramAnonymousAdapterView);
            }
        });

    }



    public void findViews()
    {
        this.siteList = ((ListView)findViewById(R.id.lv));
//        this.wv = ((WebView)findViewById(R.id.wv));
    }


    /////////////////////////////////////


}
