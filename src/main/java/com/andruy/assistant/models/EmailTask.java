package com.andruy.assistant.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailTask {
    private int timeframe;
    private Email email;
}
