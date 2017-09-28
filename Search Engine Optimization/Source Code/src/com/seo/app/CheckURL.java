package com.seo.app;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;
/*It is used to check the validity of the URL entered by the user. If an invalid URL is entered by the user, 
 * an appropriate message will be shown and the program exits smoothly.*/
    class CheckURL  {
    static Logger log = Logger.getLogger(CheckURL.class.getName());	
	private URL url;
	private URI uri;
	CheckURL(String inputURL) 
	{
		urlValidity(inputURL);		
	}	
	private void urlValidity(String inputURL) {     // Checks the validity of the URL
		// TODO Auto-generated method stub
		try
		{
			this.url = new URL(inputURL);
			this.uri = this.url.toURI();		   
		}
		catch (MalformedURLException e)    // Catch Invalid URL exception
		{
			log.severe("URL provided is incorrect ! Please retry again with the correct format, For Example - http://www.brightedge.com ");     
            System.exit(0);            
		}	
		catch (URISyntaxException e)   // Catch Invalid URI exception
		{			
			log.severe("URL provided is incorrect ! Please retry again with the correct format, For Example - http://www.brightedge.com ");            
            System.exit(0);            
		}			
	}
}
