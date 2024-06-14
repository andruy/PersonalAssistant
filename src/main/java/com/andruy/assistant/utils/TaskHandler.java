package com.andruy.assistant.utils;

import java.time.LocalDateTime;
import java.util.UUID;

import com.andruy.assistant.models.EmailTask;
import com.andruy.assistant.models.PushNotification;
import com.andruy.assistant.models.TaskId;
import com.andruy.assistant.services.EmailService;
import com.andruy.assistant.services.PushNotificationService;

public class TaskHandler extends Thread {
    private EmailTask task;
    private Thread thread;

    public TaskHandler(EmailTask task) {
        this.task = task;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            if (task.getTimeframe() < System.currentTimeMillis()) {
                execute();
            } else {
                TaskId params = new TaskId(UUID.randomUUID().toString(), task.getEmail().getSubject(), task.getTime());

                Promise.add(params, thread);

                Thread.sleep(task.getTimeframe() - System.currentTimeMillis());

                execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void execute() {
        new EmailService().sendEmail(task.getEmail());
        System.out.println("Email sent at " + LocalDateTime.now().toString().substring(0, 16));
        new PushNotificationService().push(new PushNotification(task.getEmail().getSubject(), "Done"));
    }
}
