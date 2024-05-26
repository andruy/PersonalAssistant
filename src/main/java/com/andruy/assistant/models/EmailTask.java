package com.andruy.assistant.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailTask {
    private long timeframe;
    private Email email;
}
