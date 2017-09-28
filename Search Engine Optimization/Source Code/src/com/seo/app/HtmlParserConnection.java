package com.seo.app;
import java.io.IOException;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/*It is a Singleton class. In this class connection from the application to HTML webpage is set, so that the 
 * webpage data can be processed.*/
    class HtmlParserConnection {
    	static Logger log = Logger.getLogger(HtmlParserConnection.class.getName());	
	private HtmlParserConnection() {}
	
	private static class HtmlParserConnectionHelper 
	{
		private static final HtmlParserConnection instance = new HtmlParserConnection();
	}
	
	public static HtmlParserConnection getInstance()       // Returns the instance of Singleton class
	{
		return HtmlParserConnectionHelper.instance;
	}	
	public Document getData(String url)    // Get the webpage data from the requested URL
	{
		Document document=null;
		 try 
		 {
	            document=Jsoup.connect(url.toString()).get();
	     }
		 catch (IOException e)   // catch IOException
		 {
			 log.severe("Could not get the data for the given URL "+url.toString());	         	      
	          System.exit(0);	         
	     }
	        return document;
	}	
	}


