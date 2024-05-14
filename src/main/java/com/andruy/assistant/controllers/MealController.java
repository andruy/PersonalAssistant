package com.andruy.assistant.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andruy.assistant.services.MealService;

@RestController
public class MealController {
    @Autowired
    private MealService mealService;

    @PostMapping("/mealup")
    public ResponseEntity<Map<String, String>> collectMeals(@RequestBody String body) {
        mealService.addMeals(body);

        return ResponseEntity.ok().body(Map.of("report", mealService.newRecordFeedback()));
    }

    @GetMapping("/mealdown")
    public ResponseEntity<Map<String, Object>> getMeals(@RequestParam(defaultValue = "0") String month) {
        mealService.getMealsTotalAmount(month);

        return ResponseEntity.ok().body(mealService.getResponse());
    }
}
