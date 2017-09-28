package com.seo.app;
import org.jsoup.nodes.Document;
import java.util.logging.*;
/*It is used for processing the General attributes of a webpage and displays its results. It has the business logic for 
 * processing the General attribute information by using the Jsoup API. */

public class GeneralAttributesHandler {
	static Logger log = Logger.getLogger(GeneralAttributesHandler.class.getName());
    GeneralAttributes ga;
	public GeneralAttributesHandler() {
		// TODO Auto-generated constructor stub
	}
	public void processAttributesElements(Document document) {
		// TODO Auto-generated method stub
		GeneralAttributesBuilder gab = new GeneralAttributesBuilder();
		gab.metarobots(document.select("meta[name=robots]").attr("content")).
		       relcanonical(document.select("link[rel=canonical]").attr("href"));
		ga = gab.build();		
	}
	public void printGeneralAttributes() {
		// TODO Auto-generated method stub
		
		log.info("\n"+"Meta Robots: " + ga.getMetarobots() + "\n" +  "Canonical link: "
	              + ga.getRelcanonical() + "\n" );
		

	}
}
