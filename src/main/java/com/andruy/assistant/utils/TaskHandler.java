package com.andruy.assistant.utils;

import com.andruy.assistant.models.EmailTask;
import com.andruy.assistant.services.EmailService;

public class TaskHandler {
    private EmailTask task;

    public TaskHandler(EmailTask task) {
        // this.emailService = emailService;
        this.task = task;
    }
    
    public void execute() {
        try {
            Thread.sleep(task.getTimeframe() * 60000);
            new EmailService().sendEmail(task.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
