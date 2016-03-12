package com.trafficscotlandfeedpullparser.feed;

import java.io.IOException;
import java.util.List;

import com.trafficscotlandfeedpullparser.feed.data.RssFeedItem;
import com.trafficscotlandfeedpullparser.feed.util.XMLPullParserHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
 
public class MainActivity extends Activity {
 
    ListView listView;
    Button ciButton;
    Button rwButton;
    Button prwButton;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        listView = (ListView) findViewById(R.id.list);
        ciButton = (Button) findViewById(R.id.curButton);
        rwButton = (Button) findViewById(R.id.rwButton);
        prwButton = (Button) findViewById(R.id.prwButton);
        
        ciButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tag", "try clicking ci");
				
				Intent intent = new Intent(getApplicationContext(), DisplayItems.class);
				Log.e("tag", "init Intent");
				
				intent.putExtra("ci", "https://trafficscotland.org/rss/feeds/currentincidents.aspx");
				intent.putExtra("id", 1);
				Log.e("tag", "put extra");

				startActivity(intent);

				Log.e("tag", "try started");
			}
		});
        
        	rwButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tag", "try clicking rw");
				
				Intent intent = new Intent(getApplicationContext(), DisplayItems.class);
				Log.e("tag", "init Intent rw");
				
				intent.putExtra("ci", "https://trafficscotland.org/rss/feeds/roadworks.aspx");
				intent.putExtra("id", 2);
				Log.e("tag", "put extra rw");

				startActivity(intent);

				Log.e("tag", "try started rw");
			}
		});
        	
        	prwButton.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				Log.e("tag", "try clicking prw");
    				
    				Intent intent = new Intent(getApplicationContext(), DisplayItems.class);
    				Log.e("tag", "init Intent");
    				
    				intent.putExtra("ci", "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");
    				intent.putExtra("id", 3);
    				Log.e("tag", "put extra");

    				startActivity(intent);

    				Log.e("tag", "try started");
    			}
    		});
        

         
        /*List<RssFeedItem> rssItems = null;
        try {
            XMLPullParserHandler parser = new XMLPullParserHandler();
            rssItems = parser.parse(getAssets().open("test.xml"));
            ArrayAdapter<RssFeedItem> adapter = 
                new ArrayAdapter<RssFeedItem>(this,R.layout.feed_display, rssItems);
            listView.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
 
 
}