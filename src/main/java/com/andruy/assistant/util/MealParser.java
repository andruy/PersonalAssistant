package com.andruy.assistant.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.andruy.assistant.model.Meal;

@Component
public class MealParser {
    public List<Meal> toMealList(String str) {
        List<Meal> list = new ArrayList<>();
        JSONArray objects = new JSONArray(str);

        for (int i = 0 ; i < objects.length(); i++) {
            JSONObject object = objects.getJSONObject(i);

            Meal meal = new Meal(
                object.getInt("price"),
                object.getString("name"),
                LocalDateTime.parse(object.getString("created_at")).toLocalDate()
            );

            list.add(meal);
        }
        return list;
    }
}
