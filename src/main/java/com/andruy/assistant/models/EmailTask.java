package com.andruy.assistant.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailTask {
    private long timeframe;
    private Email email;

    public String getTime() {
        Date date = new Date(timeframe);

        return date.toString();
    }
}
