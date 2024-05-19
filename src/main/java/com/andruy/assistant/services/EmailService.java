package com.andruy.assistant.services;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.andruy.assistant.models.Email;
import com.andruy.assistant.models.EmailTask;

@Component
@Scope("prototype")
public class EmailService {
    @Value("${my.email.username}")
    private String username;
    @Value("${my.email.password}")
    private String password;
    @Value("${my.email.host}")
    private String host;
    @Value("${my.email.port}")
    private String port;
    @Value("${my.email.recipient}")
    private String recipient;
    private Session session;
    private Properties props;
    private Authenticator authenticator;
    private String feedback = "Not processed";
    private String name = "Personal Assistant";
    private static final List<String> tasks = new CopyOnWriteArrayList<>();

    public void sendEmail(Email email, String type) {
        // Setup properties for the mail session
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // Get the default Session object
        session = Session.getInstance(props, authenticator);

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(username, name));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));

            // Set Subject: header field
            message.setSubject(email.getSubject());

            // Now set the actual message
            message.setContent(email.getBody(), type);

            // Send message
            Transport.send(message);

            feedback = "Email sent successfully!";
            System.out.println(feedback);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Email email) {
        // Setup properties for the mail session
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // Get the default Session object
        session = Session.getInstance(props, authenticator);

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(username, name));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));

            // Set Subject: header field
            message.setSubject(email.getSubject());

            // Now set the actual message
            message.setText(email.getBody());

            // Send message
            Transport.send(message);

            feedback = "Email sent successfully!";
            System.out.println(feedback);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void addTask(EmailTask emailTask) {
        tasks.add(emailTask.getEmail().getSubject() + " - " + emailTask.getTimestamp().plusMinutes(emailTask.getTimeframe()));
    }

    public List<String> getTasks() {
        return tasks;
    }

    @Async
    public void taskedEmail(EmailTask emailTask) {
        try {
            Thread.sleep(60000 * emailTask.getTimeframe());
            sendEmail(emailTask.getEmail());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getFeedback() {
        return feedback;
    }
}
