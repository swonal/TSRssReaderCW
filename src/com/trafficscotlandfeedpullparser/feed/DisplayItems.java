package com.trafficscotlandfeedpullparser.feed;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.trafficscotlandfeedpullparser.feed.data.RssFeedItem;
import com.trafficscotlandfeedpullparser.feed.util.XMLPullParserHandler;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
 
public class DisplayItems extends Activity {
 
    ListView listView;
    String xml;
    private List<RssFeedItem> rssItems = new ArrayList<RssFeedItem>();
    //Indicate which feed was selected
    int id;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_show);
         
        Log.e("tag", "beginning");
        
        listView = (ListView) findViewById(R.id.list);
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        
        if(extras != null){
			 try {
				 Log.e("tag", "Parse xml");
				xml = sourceListingString(extras.getString("ci"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("tag", "xml not assigned");
				e.printStackTrace();
			}
        }
        
        Log.e("tag", "before parse");
        
        
        XMLPullParserHandler parser = new XMLPullParserHandler();
        Log.e("tag", "before parser.parse xml" + xml);
		rssItems = parser.parse(xml, id);
		Log.e("tag", "before adapter" + rssItems.toString());
		ArrayAdapter<RssFeedItem> adapter = new MyListAdapter();
//		    new ArrayAdapter<RssFeedItem>(this,R.layout.item_view, rssItems);
		Log.e("tag", "before set adapter");
		listView.setAdapter(adapter);
    }
    
    
 


	private class MyListAdapter extends ArrayAdapter<RssFeedItem> {
    	public MyListAdapter(){
    		super(DisplayItems.this, R.layout.item_view, rssItems);
    	}
		
    	public View getView(int position, View convertView, ViewGroup parent){
    		//make sure there is a view
    		View itemView = convertView;
    		if (itemView == null){
    			itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
    		}
    		
    		//find the feed
    		RssFeedItem currentFeed = rssItems.get(position);
    		
    		//title
    		TextView titleText = (TextView) itemView.findViewById(R.id.txtTitle);
    		titleText.setText(currentFeed.getTitle());
    		
    		//description
    		TextView descText = (TextView) itemView.findViewById(R.id.txtDesc);
    		descText.setText(currentFeed.getDescription());
    		
    		//startDate
    		TextView stdText = (TextView) itemView.findViewById(R.id.txtStartDate);
    		stdText.setText("Start Date: " + currentFeed.getStartDate());
    		
    		//EndDate
    		TextView endText = (TextView) itemView.findViewById(R.id.txtEndDate);
    		endText.setText("End Date: " + currentFeed.getEndDate());
    		
    		//Link
    		TextView pubDateText = (TextView) itemView.findViewById(R.id.txtLink);
    		pubDateText.setText(currentFeed.getLink());
    		Log.e("tag", "before return itemView");
    		return itemView;
    	}
	}



	private static String sourceListingString(String urlString)throws IOException
    {
	 	String result = "";
		InputStream anInStream = null;
    	int response = -1;
    	URL url = new URL(urlString);
    	URLConnection conn = url.openConnection();
    	
    	Log.e("tag", "in inputstream 1" + url);
    	
    	// Check that the connection can be opened
    	if (!(conn instanceof HttpURLConnection))
    			throw new IOException("Not an HTTP connection");
    	try
    	{
    		// Open connection
    		Log.e("tag", "in inputstream 1.1");
    		HttpURLConnection httpConn = (HttpURLConnection) conn;
    		Log.e("tag", "in inputstream 1.2");
    		httpConn.setAllowUserInteraction(false);
    		Log.e("tag", "in inputstream 1.3");
    		httpConn.setInstanceFollowRedirects(true);
    		Log.e("tag", "in inputstream 1.4");
    		httpConn.setRequestMethod("GET");
    		Log.e("tag", "in inputstream 1.5");
    		try{
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		StrictMode.setThreadPolicy(policy);
    		httpConn.connect();
    		}catch (Exception ec){
    			Log.e("tag", "in inputstream 1.5" + ec);
    		}
    		Log.e("tag", "in inputstream 1.6");
    		response = httpConn.getResponseCode();
    		
    		Log.e("tag", "in inputstream 2: " + response);
    		
    		// Check that connection is Ok
    		if (response == HttpURLConnection.HTTP_OK)
    		{
    			// Connection is Ok so open a reader 
    			anInStream = httpConn.getInputStream();
    			InputStreamReader in= new InputStreamReader(anInStream);
    			BufferedReader bin= new BufferedReader(in);
    			
    			Log.e("tag", "in inputstream 3");
    			
    			// Read in the data from the RSS stream
    			String line = new String();
    			// Read past the RSS headers
    			bin.readLine();
    			//bin.readLine();
    			// Keep reading until there is no more data
    			while (( (line = bin.readLine())) != null)
    			{
    				
    				result = result + "\n" + line;
    			}
    		}
    	}                
    	catch (Exception ex)
    	{
    			throw new IOException("Error connecting");
    	}
    	Log.e("tag", "in inputstream 4");
    	// Return result as a string for further processing
    	return result;
    	
    } // End of sourceListingString
    

    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
}