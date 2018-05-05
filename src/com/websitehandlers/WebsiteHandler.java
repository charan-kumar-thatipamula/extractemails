package com.websitehandlers;

public interface WebsiteHandler {
	public void handleRequest();
	public void setOutputCSVName(String csvName);
	public String getOutputFileName();
}
