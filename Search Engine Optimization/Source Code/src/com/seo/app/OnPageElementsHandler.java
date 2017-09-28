package com.seo.app;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/*It is used for processing the On-page elements of a webpage and displays its results. It has the business logic for 
 * processing the On-page  elements information by using the Jsoup API*/

public class OnPageElementsHandler {
	List<String> imagelist;
	List<String> header2list;
	List<String> header1list;
	OnPageElements ope;
	static Logger log = Logger.getLogger(OnPageElementsHandler.class.getName());

	public OnPageElementsHandler() {
		// TODO Auto-generated constructor stub	
		imagelist = new ArrayList<String>();
		header1list = new ArrayList<String>();
		header2list = new ArrayList<String>();
	}
	public void processOnPageElements(Document document)
	{		
		Elements element = document.getAllElements();
		for(Element header1element : element)
		{
			if(header1element.nodeName().matches("h[1]"))
				header1list.add(header1element.text());
		}	
		for(Element header2element : element)
		{
			if(header2element.nodeName().matches("h[2]"))
				header2list.add(header2element.text());
		}		
		 Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
	        for (Element image : images) {
	        	   if(!image.attr("alt").isEmpty())
	        	   {
	        	   imagelist.add(image.attr("alt"));
	        	   }		
		OnPageElementsBuilder oeb = new OnPageElementsBuilder();
		oeb.url("").
		       title(document.title()).
		       metadescription(document.select("meta[name=description]").get(0).attr("content")).
		       keywords(document.select("meta[name=keywords]").attr("content")).
		       header1list(header1list).
		       header2list(header2list).
               imageList(imagelist);
		       ope = oeb.build();		       
	}
	}
	public void printOnPageElements(String url) {		// TODO Auto-generated method stub     
		log.info("\n"+"URL: " + url + "\n"
	              + "Title: " + ope.getTitle() + "\n" + "Meta-Description:  "
	              + ope.getMetadescription() + "\n" + "Keywords: " + ope.getKeywords() + 
	              "\n" +"Header1: " + ope.header1list + "\n" + "Header2: " + ope.header2list+
	              "\n" + "Image description: " + ope.imagelist);
	}
}
