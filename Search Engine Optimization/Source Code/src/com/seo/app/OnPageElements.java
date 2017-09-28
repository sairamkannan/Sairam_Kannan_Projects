package com.seo.app;
import java.util.ArrayList;
import java.util.List;
/*It has the header and setter methods for all the On-page elements ( URL, title, Meta-description, Meta-keyword, 
 * Header-1, Header-2, Image-list ). It is a Plain Old Java Object (POJO) class*/
    class OnPageElements {	
	private String url;
	private String title;
	private String metadescription;
	private String keywords;
	List<String> header1list;
	List<String> header2list;
	List<String> imagelist;	
	public OnPageElements() {
		// TODO Auto-generated constructor stub
		imagelist = new ArrayList<String>();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMetadescription() {
		return metadescription;
	}
	public void setMetadescription(String metadescription) {
		this.metadescription = metadescription;
	}
	public String getKeywords() {
				return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public List<String> getHeader1() {
			return header1list;
	}
	public void setHeader1(List<String> header1) {
		this.header1list = header1;
	}	
	public List<String>getHeader2() {			
		return header2list;
	}
	public void setHeader2(List<String> header2) {
		this.header2list = header2;
		}
	public List<String> getImagelist() {
		
		return imagelist;
	}
	public void setImagelist(List<String> imagelist) {
		this.imagelist = imagelist;
	}	
}
