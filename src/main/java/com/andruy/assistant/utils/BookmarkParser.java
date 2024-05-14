package com.andruy.assistant.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.andruy.assistant.models.Bookmark;

@Component
public class BookmarkParser {
    public List<Bookmark> toBookmarkList(String str) {
        List<Bookmark> list = new ArrayList<>();
        JSONArray objects = new JSONArray(str);

        for (int i = 0 ; i < objects.length(); i++) {
            JSONObject object = objects.getJSONObject(i);

            Bookmark bookmark = new Bookmark(
                object.getString("reference"),
                object.getString("link"),
                LocalDate.parse(object.getString("date")),
                object.getInt("id")
            );

            list.add(bookmark);
        }
        return list;
    }
}
