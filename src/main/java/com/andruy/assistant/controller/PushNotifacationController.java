package com.andruy.assistant.controller;

import org.springframework.web.bind.annotation.RestController;

import com.andruy.assistant.model.PushNotification;
import com.andruy.assistant.service.PushNotificationService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PushNotifacationController {
    @Autowired
    private PushNotificationService pushNotificationService;
    
    @PostMapping("/push")
    public ResponseEntity<Map<String, Integer>> push(@RequestBody PushNotification msg) {
        return ResponseEntity.ok().body(Map.of("report", pushNotificationService.push(msg)));
    }
    
}
