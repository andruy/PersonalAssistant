package com.andruy.assistant.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.andruy.assistant.service.InstagramService;

@Controller
public class InstagramController {
    @Autowired
    private InstagramService instagramService;

    @GetMapping("/followers")
    public ResponseEntity<Map<String, String>> getFollowers() {
        return ResponseEntity.ok(instagramService.getFollowers());
    }

    @GetMapping("/following")
    public ResponseEntity<Map<String, String>> getFollowing() {
        return ResponseEntity.ok(instagramService.getFollowing());
    }

    @GetMapping("/compare")
    public ResponseEntity<Map<String, String>> getFollowersComparison() {
        instagramService.getComparison();
        return ResponseEntity.ok(Map.of("report", "You will be notified when the task is done"));
    }

    @GetMapping("/listOfDates")
    public ResponseEntity<List<Date>> getListOfDates() {
        return ResponseEntity.ok(instagramService.getListOfDates());
    }

    @GetMapping("/listOfAccounts")
    public ResponseEntity<Map<String, String>> getListOfAccounts(@RequestParam("date") Date date) {
        return ResponseEntity.ok(instagramService.getListOfAccounts("NMF", date));
    }

    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<Map<String, String>> deleteAccounts(@RequestParam("date") Date date, @RequestBody List<String> list) {
        instagramService.deleteAccounts("NMF", date, list);
        return ResponseEntity.ok(Map.of("report", "You will be notified when the task is done"));
    }

    @PutMapping("/protectAccounts")
    public ResponseEntity<Map<String, String>> protectAccounts(@RequestParam("date") Date date, @RequestBody List<String> list) {
        return ResponseEntity.ok(instagramService.protectAccounts("NMF", date, list));
    }
}
