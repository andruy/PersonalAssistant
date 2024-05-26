package com.andruy.assistant.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.andruy.assistant.models.EmailTask;
import com.andruy.assistant.utils.TaskHandler;

@Component
public class EmailTaskService {
    private EmailTask task;

    @Async
    public void addTask(EmailTask task) {
        this.task = task;

        new TaskHandler(task).execute();
    }

    public String report() {
        return task.getTimeframe() == 1 ? "Email will be sent in " + task.getTimeframe() + " minute" :
        "Email will be sent in " + task.getTimeframe() + " minutes";
    }
}
