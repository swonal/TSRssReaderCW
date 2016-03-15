package com.trafficscotlandfeedpullparser.feed.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.trafficscotlandfeedpullparser.feed.data.RssFeedItem;

public class XMLPullParserHandler {
	List<RssFeedItem> rssItems;
	private RssFeedItem rssItem;
	private String temp;
	
	public XMLPullParserHandler(){
		rssItems = new ArrayList<RssFeedItem>();
	}
	
	public List<RssFeedItem> getRssItems(){
		return rssItems;
	}
	
	public List<RssFeedItem> parse(String xml, int id){
		Log.e("tag", "in parse()");
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;
		try{ 
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();
			
			parser.setInput(new StringReader(xml));
			
			Log.e("tag", "before parser.getEvent");
			
			int eventType = parser.getEventType();
			
			rssItem = new RssFeedItem();
			
            boolean inItem = false;
            boolean currentIncident = false;
            
			while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();

                switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("item")) {
                        // create a new instance of rssFeedItem
                        rssItem = new RssFeedItem();
                        inItem = true;
                    }
                    break;
 
                case XmlPullParser.TEXT:
                    temp = parser.getText();
                    break;
 
                case XmlPullParser.END_TAG:
                	if(inItem){
                    if (tagname.equalsIgnoreCase("item")) {
                        // add rss object to list
                        rssItems.add(rssItem);
                    } else if (tagname.equalsIgnoreCase("title")) {
                    	rssItem.setTitle(temp);
                        Log.e("pulltag", temp);
                    } else if (tagname.equalsIgnoreCase("description")) {
                        if(id == 2 || id == 3){
                    	int eed;
                        String descTemp;                        
                    	int sd = temp.indexOf("Start Date:")+12;
                        int esd = temp.indexOf("<br />");
                        int ed = temp.indexOf("End Date:")+10;
                        if(temp.lastIndexOf("<br />") == esd){
                        	eed = temp.length();
                        	descTemp = "";
                        }else{
                        	eed = temp.lastIndexOf("<br />");
                        	int stdes = eed + 6;
                        	int endes = temp.length();
                        	descTemp = temp.substring(stdes, endes).replaceAll("<br/>", "");
                        }
                        String std = temp.substring(sd, esd);
                        String end = temp.substring(ed, eed);
                        rssItem.setStartDate(std);
                        rssItem.setEndDate(end);
                    	rssItem.setDescription(descTemp);}
                        else{
                            rssItem.setEndDate("TBA");
                        	rssItem.setDescription(temp);
                        	currentIncident = true;
                        }
                    } else if (tagname.equalsIgnoreCase("link")) {
                        rssItem.setLink(temp);
                    } else if (tagname.equalsIgnoreCase("pubDate")) {
                        if(currentIncident){
                        	rssItem.setStartDate(temp);
                        	currentIncident = false;
                        }                    	
                    }
                	}
                    break;
 
                default:
                    break;
                }
                eventType = parser.next();
            }
 
        } catch (XmlPullParserException es) {
            es.printStackTrace();
            Log.e("pullerror", es.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
		Log.e("tag", "returning rssItem");
        return rssItems;
		}
	
		
	}
	


