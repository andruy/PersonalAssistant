package com.andruy.assistant.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushNotification {
    private String title, body;
}
