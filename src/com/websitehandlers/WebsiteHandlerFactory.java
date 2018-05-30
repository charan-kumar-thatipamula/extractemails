package com.websitehandlers;

import com.sciencedirect.ScienceDirectHandler;

public class WebsiteHandlerFactory {
	public static WebsiteHandler getWebsiteHandler(String url) {
		if (url.indexOf("journals.plos") != -1) {
			return new JournalPlosHandler(url);
		} else if (url.indexOf("sciencedirect.com") != -1) {
			return new ScienceDirectHandler(url);
		}
		return null;
	}
}
