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
import com.andruy.assistant.services.EmailTaskService;

@Controller
public class EmailTaskController {
    @Autowired
    private EmailTaskService emailTaskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<String>> getTasks() {
        return ResponseEntity.ok().body(emailTaskService.getTaskTemplate());
    }

    @GetMapping("/emailtask")
    public ResponseEntity<Map<String, List<String>>> getEmailTasks() {
        // return ResponseEntity.ok().body(Map.of("Email tasks", emailTaskService.getTasks()));
        return null;
    }

    @PostMapping("/emailtask")
    public ResponseEntity<Map<String, String>> emailAgentTest(@RequestBody EmailTask body) {
        emailTaskService.addTask(body);

        return ResponseEntity.ok().body(Map.of("report", emailTaskService.report()));
    }
}
