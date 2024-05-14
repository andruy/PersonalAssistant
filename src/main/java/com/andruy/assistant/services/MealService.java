package com.andruy.assistant.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.andruy.assistant.models.Meal;
import com.andruy.assistant.utils.MealParser;

@Component
public class MealService {
    @Value("${my.supabase.key}")
    private String apiKey;
    @Value("${my.supabase.url.get.meals}")
    private String getMeals;
    @Value("${my.supabase.url.insert.meal}")
    private String insertMeal;
    @Autowired
    private MealParser parser;
    private int statusCode;
    private int monthlyCost;
    private List<Meal> list;
    private HttpClient httpClient;
    private HttpRequest httpRequest;
    private Map<String, Object> jsonResponse;

    public void addMeals(String jsonPayload) {
        httpClient = HttpClient.newHttpClient();

        httpRequest = HttpRequest.newBuilder()
            .header("apikey", apiKey)
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
            .uri(URI.create(insertMeal))
            .build();

        statusCode = httpClient.sendAsync(httpRequest, BodyHandlers.ofString())
        .thenApply(HttpResponse::statusCode)
        .join();
    }

    public void getMealsTotalAmount(String param) {
        int month;

        httpClient = HttpClient.newHttpClient();

        httpRequest = HttpRequest.newBuilder()
            .header("apikey", apiKey)
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .uri(URI.create(getMeals))
            .build();

        String response = httpClient.sendAsync(httpRequest, BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .join();

        List<Meal> meals = parser.toMealList(response);
        list = new ArrayList<>();

        if (Integer.parseInt(param) > 0 && Integer.parseInt(param) < 13 ) {
            month = Integer.parseInt(param);
        } else {
            month = LocalDate.now().getMonthValue();
        }

        for (Meal meal : meals) {
            if (meal.getDate().getMonthValue() == month) {
                list.add(meal);
            }
        }

        calculateTotal(list);
    }

    private void calculateTotal(List<Meal> meals) {
        monthlyCost = 0;
        
        for (Meal meal : meals) {
            int mealTotal = meal.getPrice();
            monthlyCost += mealTotal;
        }
    }

    public Map<String, Object> getResponse() {
        jsonResponse = new HashMap<>();

        jsonResponse.put("numberOfMeals", list.size());
        jsonResponse.put("totalAmount", monthlyCost);
        jsonResponse.put("listOfMeals", list);

        return jsonResponse;
    }

    public String newRecordFeedback() {
        return statusCode >= 200 && statusCode < 300 ?
        "Sucessful at " + LocalDateTime.now().toString().substring(0, 16) :
        "NOT sucessful at " + LocalDateTime.now().toString().substring(0, 16);
    }
}
