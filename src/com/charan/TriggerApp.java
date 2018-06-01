package com.charan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.charan.communication.SendEmailWrapper;
import com.charan.websites.AbstractWebsiteHandler;
import com.charan.websites.WebsiteHandlerFactory;

public class TriggerApp {

	private void process() {
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


		SendEmailWrapper sendEmailWrapper = new SendEmailWrapper();
		for (String url1 : urls) {
			AbstractWebsiteHandler wh = WebsiteHandlerFactory.getWebsiteHandler(url1);
//			String fName = "outputcsv" + new Date().getTime() + ".csv";
//			wh.setOutputCSVName(fName);
			wh.handleRequest();
			sendEmailWrapper.addCSVFileJobs(wh.getOutputFileName());
		}
		scanner.close();
//		sendEmailWrapper.triggerEmail();
	}

	public static void main(String[] args) {
//		http://journals.plos.org/plosone/dynamicSearch?resultsPerPage=60&q=author_affiliate%3A%22Dermatology+%22&sortOrder=DATE_NEWEST_FIRST&page=1
//		Scanner scanner = new Scanner(System.in);
//		List<String> urls = new ArrayList<String>();
//		while (true) {
//			System.out.println("Enter URL to extract journal details or Press 'Enter'");
//			String url = scanner.nextLine();
//			if (url == null || url.length() == 0) {
//				break;
//			}
//			urls.add(url);
//		}
//
//		for (String url1 : urls) {
//			AbstractWebsiteHandler wh = WebsiteHandlerFactory.getWebsiteHandler(url1);
//			String fName = "outputcsv" + new Date().getTime() + ".csv";
//			wh.setOutputCSVName(fName);
//			wh.handleRequest();
//		}
//		scanner.close();
		TriggerApp triggerApp = new TriggerApp();
		triggerApp.process();
	}
}
