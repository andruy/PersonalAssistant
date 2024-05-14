package com.andruy.assistant.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.andruy.assistant.models.PushNotification;
import com.andruy.assistant.services.PushNotificationService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PushNotifacationController {
    @Autowired
    private PushNotificationService pushService;
    
    @PostMapping("/push")
    public ResponseEntity<Map<String, String>> push(@RequestBody PushNotification msg) {
        pushService.push(msg);
        
        return ResponseEntity.ok().body(Map.of("report", pushService.getResponse()));
    }
    
}
