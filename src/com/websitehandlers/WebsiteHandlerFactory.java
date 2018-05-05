package com.websitehandlers;

public class WebsiteHandlerFactory {
	public static WebsiteHandler getWebsiteHandler(String url) {
		if (url.indexOf("journals.plos") != -1) {
			return new JournalPlosHandler(url);
		}
		return null;
	}
}
