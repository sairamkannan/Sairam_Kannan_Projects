package com.seo.app;
import java.util.List;

/*I used Builder pattern for setting and getting the values of On-page elements.  Since the parameters required for 
 * On-page elements are expected to grow (since I have used only some), Builder pattern provides a better way to 
 * construct the object for the On-page elements*/

    class OnPageElementsBuilder {
	private OnPageElements ope;
	public OnPageElementsBuilder(){
		ope = new OnPageElements();
	}	
	public OnPageElementsBuilder url(String url){
		ope.setUrl(url);
		return this;
	}	
	public OnPageElementsBuilder title(String title){
		ope.setTitle(title);
		return this;
	}	
	public OnPageElementsBuilder metadescription(String metadescription){
		ope.setMetadescription(metadescription);
		return this;
	}	
	public OnPageElementsBuilder keywords(String keywords){
		ope.setKeywords(keywords);
		return this;
	}	
	public OnPageElementsBuilder header1list(List<String> header1list){
		ope.setHeader1(header1list);
		return this;
	}	
	public OnPageElementsBuilder header2list(List<String> header2list){
		ope.setHeader2(header2list);
		return this;
	}	
	public OnPageElementsBuilder imageList(List<String> imagelist){
		ope.setImagelist(imagelist);
		return this;
	}	
	public OnPageElements build(){
		return ope;
	}
}
