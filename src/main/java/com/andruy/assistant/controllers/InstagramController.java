package com.andruy.assistant.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.andruy.assistant.services.InstagramService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class InstagramController {
    @Autowired
    private InstagramService instagramService;

    @PostMapping("/ig")
    public ResponseEntity<List<List<String>>> getInstagramList(@RequestBody Map<String, String> body) {

        return ResponseEntity.ok().body(instagramService.getList(body));
    }
}
