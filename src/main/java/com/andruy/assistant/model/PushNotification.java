package com.andruy.assistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushNotification {
    private String title, body;
}
