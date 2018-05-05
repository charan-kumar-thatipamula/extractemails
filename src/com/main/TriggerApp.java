package com.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.factory.ResponseHandlerFactory;
import com.httprequest.SendRequest;
import com.util.ResponseHandler;
import com.websitehandlers.WebsiteHandler;
import com.websitehandlers.WebsiteHandlerFactory;

public class TriggerApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		http://journals.plos.org/plosone/dynamicSearch?resultsPerPage=60&q=author_affiliate%3A%22Dermatology+%22&sortOrder=DATE_NEWEST_FIRST&page=1
		Scanner scanner = new Scanner(System.in);
		List<String> urls = new ArrayList<String>();
		while (true) {
			System.out.println("Enter URL to extract journal details or Press 'Enter'");
			String url = scanner.nextLine();
			if (url == null || url.length() == 0) {
				break;
			}
			urls.add(url);
		}
		
		for (String url1 : urls) {
			WebsiteHandler wh = WebsiteHandlerFactory.getWebsiteHandler(url1);
			wh.setOutputCSVName("outputcsv" + new Date().getTime() + ".csv");
			wh.handleRequest();
		}
		scanner.close();
	}
}