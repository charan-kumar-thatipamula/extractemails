package com.charan;

import java.util.*;

import com.charan.communication.SendEmailWrapper;
import com.charan.websites.AbstractWebsiteHandler;
import com.charan.websites.WebsiteHandlerFactory;

public class TriggerApp {

	private void process() {
		if (notValid()) {
			return;
		}
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

		if (notValid()) {
			return;
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
		sendEmailWrapper.triggerEmail();
	}

	public boolean notValid() {
		Calendar cal = Calendar.getInstance();
		// You cannot use Date class to extract individual Date fields
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);      // 0 to 11
		int day = cal.get(Calendar.DAY_OF_MONTH);
//		System.out.println(year + " " + month + " " + day);
		if (year > 2018)
			return true;
		if (month > 6)
			return true;
		return false;
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
