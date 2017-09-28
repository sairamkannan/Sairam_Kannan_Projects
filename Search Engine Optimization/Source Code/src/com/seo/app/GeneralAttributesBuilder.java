package com.seo.app;
/*I used Builder pattern for setting and getting the values of General attributes of a webpage.  
 * Since the parameters required for General attributes are expected to grow (since I have used only some), 
 * Builder pattern provides a better way to construct the object for the General attributes of a webpage.*/    
class GeneralAttributesBuilder {
	private GeneralAttributes ga;
	public GeneralAttributesBuilder(){
		ga = new GeneralAttributes();
	}
	public GeneralAttributesBuilder metarobots(String metarobots){
		ga.setMetarobots(metarobots);
		return this;
	}
	public GeneralAttributesBuilder relcanonical(String relcanonical){
		ga.setRelcanonical(relcanonical);
		return this;
	}
	public GeneralAttributes build(){
		return ga;
	}	
}
