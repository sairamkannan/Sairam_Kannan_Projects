package com.seo.app;
import org.jsoup.nodes.Document;
    /* It is the starting point of the application. It takes the URL input from the user (from command line arguments ),
        initiates a validity check of the URL, initiates a connection for accessing the required page and calls the 
        appropriate class for processing the data and get the results */

    class SEO_Main {
	private String inputURL;
	private Document document;
	OnPageElementsHandler oeh;
	GeneralAttributesHandler gah;

	public static void main(String args[]) 
	{
		SEO_Main seo = new SEO_Main();
		if(args.length>1 || args.length<1)     //To check the number of URL given as input in command line argument
		{
			throw new IllegalArgumentException("Exactly one URL is required");
		}		
		seo.setURL(args);
	    seo.checkURL();	        // Method to check the format of the URL
	    seo.setConnection();   // Method for HTML parser connection
	    seo.processDocument();  // Business logic for processing the data
	    
	}
	private void processDocument() {
		// TODO Auto-generated method stub
		oeh = new OnPageElementsHandler();
		gah = new GeneralAttributesHandler();
		oeh.processOnPageElements(document);
		oeh.printOnPageElements(inputURL);
		gah.processAttributesElements(document);
		gah.printGeneralAttributes();
	}
	private void setConnection() {
		// TODO Auto-generated method stub
		document = HtmlParserConnection.getInstance().getData(inputURL);   // Accessing the Singleton HtmlParserConnection class	   	
	}
	private void setURL(String args[]) {
		// TODO Auto-generated method stub
 		inputURL = args[0]; 				
	}
	private void checkURL()  
	{
		new CheckURL(inputURL);
	}
}
