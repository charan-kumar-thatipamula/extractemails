package com.charan.websites;

import com.charan.file.FileUility;
import com.charan.websites.sciencedirect.ExtractJournalMetadataSD;

import java.util.List;

public abstract class AbstractWebsiteHandler {

	String url;

	public void handleRequest() {
		List<String> journalUrls = extractJournalUrls(getUrl());
		String header = "Title,Author,Email" + "\n";
		String fData = processJournals(journalUrls);
		fData = header + fData;
//		ExtractJournalMetadataSD extractJournalMetadataSD = new ExtractJournalMetadataSD();
//		for (String journalUrl : journalUrls) {
////			String contents = extractJournalMetadataSD.getJournalMetadata(journalUrl);
//			String contents = getJournalMetadata(journalUrl);
//			if (contents.indexOf("@") == -1)
//				continue;
//			System.out.println("contents: " + contents);
//			fData = fData + contents;
//		}
		FileUility fileUility = new FileUility();
//        String fName = "outputcsv" + new Date().getTime() + ".csv";
		fileUility.setOutputFileName(getOutputFileName());
		fileUility.writeOutputFile(fData);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

//	public abstract void setOutputCSVName(String csvName);

	public abstract List<String> extractJournalUrls(String url);

	public abstract String processJournals(List<String> journalUrls);

	public abstract String getOutputFileName();
}
