package com.andruy.assistant.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    private String reference;
    private String link;
    private LocalDate date;
    private int id;
}
