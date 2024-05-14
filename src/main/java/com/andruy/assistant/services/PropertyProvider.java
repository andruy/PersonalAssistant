package com.andruy.assistant.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PropertyProvider {
    @Value("${my.email.recipient}")
    private String receiver;
    @Value("${my.programming.directory}")
    private String programmingDirectory;
    @Value("${my.yt.downloader}")
    private String ytd;
    @Value("${my.bin.bash}")
    private String bin;

    @PostConstruct
    public void init() {
        System.setProperty("receiver", receiver);
        System.setProperty("programmingDirectory", programmingDirectory);
        System.setProperty("ytd", ytd);
        System.setProperty("bin", bin);
    }
}
