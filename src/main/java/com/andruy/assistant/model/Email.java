package com.andruy.assistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Email {
    private String to, subject, body;
}
