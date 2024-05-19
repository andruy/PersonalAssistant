package com.andruy.assistant.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.andruy.assistant.models.EmailTask;
import com.andruy.assistant.services.EmailService;

@Controller
public class EmailTaskController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/emailtask")
    public ResponseEntity<Map<String, List<String>>> getEmailTasks() {
        return ResponseEntity.ok().body(Map.of("Email tasks", emailService.getTasks()));
    }

    @PostMapping("/emailtask")
    public ResponseEntity<Map<String, String>> emailAgentTest(@RequestBody EmailTask body) {
        emailService.addTask(body);
        emailService.taskedEmail(body);

        String feedback = "Not processed";
        if (body.getTimeframe() == 1) {
            feedback = "Email will be sent in " + body.getTimeframe() + " minute";
        } else {
            feedback = "Email will be sent in " + body.getTimeframe() + " minutes";
        }

        return ResponseEntity.ok().body(Map.of("report", feedback));
    }
}
