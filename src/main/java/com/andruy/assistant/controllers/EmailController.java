package com.andruy.assistant.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.andruy.assistant.models.Email;
import com.andruy.assistant.services.EmailService;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> emailAgent(@RequestBody Email email) {
        emailService.sendEmail(email);

        return ResponseEntity.ok().body(Map.of("report", emailService.getFeedback()));
    }
}
