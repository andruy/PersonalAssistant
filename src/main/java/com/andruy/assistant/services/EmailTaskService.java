package com.andruy.assistant.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.andruy.assistant.models.EmailTask;
import com.andruy.assistant.utils.TaskHandler;

@Component
public class EmailTaskService {
    @Value("${dir.corrections}")
    private String dataFile;
    private Scanner scanner;
    private StringBuilder sb;
    private EmailTask task;

    public List<String> getTaskTemplate() {
        List<String> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(getTaskList()).getJSONObject("EmailActions");

        jsonObject.keys().forEachRemaining(key -> list.add(jsonObject.get(key).toString()));

        return list;
    }

    @Async
    public void addTask(EmailTask task) {
        this.task = task;

        new TaskHandler(task).execute();
    }

    private String getTaskList() {
        sb = new StringBuilder();

        try {
            scanner = new Scanner(new File(dataFile));
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public String report() {
        Date date = new Date(task.getTimeframe());
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime localTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    
        return task.getTimeframe() < System.currentTimeMillis() ? "Sending email now" :
            "Sending email on " + localDate + " at " + localTime;
    }
}
