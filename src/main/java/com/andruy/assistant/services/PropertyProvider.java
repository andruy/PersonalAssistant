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
    @Value("${my.ssh.host}")
    private String sshHost;
    @Value("${my.ssh.username}")
    private String sshUsername;
    @Value("${my.ssh.password}")
    private String sshPassword;
    @Value("${dir.send.email}")
    private String sendEmail;

    @PostConstruct
    public void init() {
        System.setProperty("receiver", receiver);
        System.setProperty("programmingDirectory", programmingDirectory);
        System.setProperty("ytd", ytd);
        System.setProperty("bin", bin);
        System.setProperty("sshHost", sshHost);
        System.setProperty("sshUsername", sshUsername);
        System.setProperty("sshPassword", sshPassword);
        System.setProperty("sendEmail", sendEmail);
    }
}
