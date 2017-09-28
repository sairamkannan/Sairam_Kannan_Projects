package com.seo.app;
/*It has the header and setter methods for some of the General attributes ( Meta robots, Canonical link ). 
 * It is a Plain Old Java Object (POJO) class.*/
    class GeneralAttributes {
    private String metarobots;
    private String relcanonical;
	public String getMetarobots() {
		return metarobots;
	}
	public void setMetarobots(String metarobots) {
		this.metarobots = metarobots;
	}
	public String getRelcanonical() {
			return relcanonical;
		}
	public void setRelcanonical(String relcanonical) {
		this.relcanonical = relcanonical;
	}
}
