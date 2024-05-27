package com.andruy.assistant.utils;

import java.time.LocalDateTime;

import com.andruy.assistant.models.EmailTask;
import com.andruy.assistant.models.PushNotification;
import com.andruy.assistant.services.EmailService;
import com.andruy.assistant.services.PushNotificationService;

public class TaskHandler {
    private EmailTask task;

    public TaskHandler(EmailTask task) {
        this.task = task;
    }

    public void execute() {
        try {
            if (task.getTimeframe() < System.currentTimeMillis()) {
                new EmailService().sendEmail(task.getEmail());
                System.out.println("Email sent at " + LocalDateTime.now().toString().substring(0, 16));
                new PushNotificationService().push(new PushNotification(task.getEmail().getSubject(), "Done"));
            } else {
                Thread.sleep(task.getTimeframe() - System.currentTimeMillis());
                new EmailService().sendEmail(task.getEmail());
                System.out.println("Email sent at " + LocalDateTime.now().toString().substring(0, 16));
                new PushNotificationService().push(new PushNotification(task.getEmail().getSubject(), "Done"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
