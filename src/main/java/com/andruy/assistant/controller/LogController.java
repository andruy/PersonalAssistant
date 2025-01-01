package com.andruy.assistant.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andruy.assistant.service.LogService;

@RestController
public class LogController {
    @Autowired
    private LogService bookmarkService;

    @GetMapping("/logReader")
    public ResponseEntity<Map<String, String>> logReader() {
        return ResponseEntity.ok(bookmarkService.logReader());
    }
}
