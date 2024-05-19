package com.andruy.assistant.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailTask {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int timeframe;
    private Email email;
}
