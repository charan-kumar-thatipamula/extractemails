package com.sciencedirect;

import com.websitehandlers.WebsiteHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.*;

public class ScienceDirectHandler implements WebsiteHandler {

    String url;
    String outputFile;
    Map<String, String> emails;
    public ScienceDirectHandler(String url) {
        this.url = url;
    }

    public ScienceDirectHandler() {

    }

    public List<String> extractJournalUrls(String url) {
        List<String> journalUrls = new ArrayList<>();
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements refs = doc.select("a[href^=/science/article/pii]");
            for (Element ref : refs) {
//                System.out.println();
                Attributes attributes = ref.attributes();
                String journalUrl = attributes.get("href");
                journalUrl = "https://www.sciencedirect.com" + journalUrl;
//                getJournalMetadata(journalUrl);
                if (map.get(journalUrl) == null) {
                    map.put(journalUrl, true);
                } else {
                    continue;
                }
                System.out.println("Journal link: " + journalUrl);
                journalUrls.add(journalUrl);
            }
        } catch (Exception e) {
            System.out.println("Exception for URL: " + url + "ignoring");
        }
        return journalUrls;
    }

    public static void main(String[] args)  throws Exception {
        ScienceDirectHandler jsoupTrial = new ScienceDirectHandler();
        List<String> journalUrls = jsoupTrial.extractJournalUrls("https://www.sciencedirect.com/search/advanced?qs=cancer&date=2018&show=25&sortBy=relevance");
        ProcessJournal scienceDirectHandler = new ProcessJournal();
        String fData = "Title,Author,Email" + "\n";
        for (String journalUrl : journalUrls) {
            String contents = scienceDirectHandler.getJournalMetadata(journalUrl);
            if (contents.indexOf("@") == -1)
                continue;
            System.out.println("contents: " + contents);
            fData = fData + contents;
        }
        FileUility fileUility = new FileUility();
        String fName = "outputcsv" + new Date().getTime() + ".csv";
        fileUility.setOutputFileName(fName);
        fileUility.writeOutputFile(fData);
    }

    @Override
    public void handleRequest() {
        List<String> journalUrls = extractJournalUrls(getUrl());
        ProcessJournal processJournal = new ProcessJournal();
        String fData = "Title,Author,Email" + "\n";
        for (String journalUrl : journalUrls) {
            String contents = processJournal.getJournalMetadata(journalUrl);
            if (contents.indexOf("@") == -1)
                continue;
            System.out.println("contents: " + contents);
            fData = fData + contents;
        }
        FileUility fileUility = new FileUility();
//        String fName = "outputcsv" + new Date().getTime() + ".csv";
        fileUility.setOutputFileName(getOutputFileName());
        fileUility.writeOutputFile(fData);
    }

    @Override
    public void setOutputCSVName(String csvName) {
        this.outputFile = csvName;
    }

    @Override
    public String getOutputFileName() {
        return outputFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public Map<String, String> getEmails() {
        return emails;
    }

    public void setEmails(Map<String, String> emails) {
        this.emails = emails;
    }

}
