package com.charan.communication;

import com.charan.communication.csv.ReadCSV;
import com.charan.communication.email.Email;
import com.charan.communication.email.GetEmailFromTemplate;
import com.charan.communication.email.EmailSendService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TriggerSendEmails implements Runnable{

    String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    String templatePath;
    String fromEmail;

    public TriggerSendEmails(String filePath, String templatePath, String fromEmail) {
        this.filePath = filePath;
        this.templatePath = templatePath;
        this.fromEmail = fromEmail;
    }

    public TriggerSendEmails () {
        this.templatePath = "emailtemplate.txt";
        this.fromEmail = "charant.lgp@gmail.com";
    }

    public void process() {
        Path path = Paths.get(filePath);
        System.out.println(path.toAbsolutePath());
        ReadCSV gm = new ReadCSV(filePath);
        List<String[]> list = gm.readFile();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        int i = 0;
        for(String[] contents : list) {
            List<String> row = Arrays.asList(contents);
            if(row.indexOf("Title") != -1) {
//                System.out.println(row.get(0) + " : " + row.get(1) + " : " + row.get(2) + " : ");
                continue;
            }
            templatePath = Paths.get("emailtemplate.txt").toAbsolutePath().toString();

            GetEmailFromTemplate et = new GetEmailFromTemplate();
            et.setTemplateFilePath(templatePath);

            Email em =  et.extractEmail(contents);
//            em.setFrom("editor.jcrm@clinicalstudiesjournal.com");
//            em.setPwd("test123!@#");
//            em.setFrom(getFromEmail());
//            em.setPwd("baaaalaiah");

//            EmailSendService emailSendService = new EmailSendService();
//            emailSendService.sendEmail(em);

            Runnable worker = new EmailSendService();
            ((EmailSendService) worker).setEm(em);
            System.out.println("Sending email : " + em.toString());
            executor.execute(worker);
            i++;
//            if (i == 5 ) {
//                break;
//            }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Done sending emails");
//        gm.setEt(et);
    }
    public static void main(String[] args) {
//        String filePath = "/Users/communication/Projects/sendemails/src/com/communication/csv/outputcsv1526668119123.csv";
        String filePath = "outputcsv1526668119123.csv";
//        String templatePath = "/Users/communication/Projects/extractemails/src/com/communication/templates/emailtemplate.txt";
//        String fromEmail = "charant.lgp+journalstest@gmail.com";
        TriggerSendEmails triggerSendEmails = new TriggerSendEmails();//filePath, templatePath, fromEmail);
        triggerSendEmails.setFilePath(filePath);
        triggerSendEmails.process();
    }

    @Override
    public void run() {
        process();
    }
}
