package com.andruy.assistant.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.andruy.assistant.models.PushNotification;

@Component
public class PushNotificationService {
    @Value("${my.ntfy.url}")
    private String ntfyUrl;
    private int statusCode;
    private HttpClient httpClient;
    private HttpRequest httpRequest;

    public void push(PushNotification msg) {
        httpClient = HttpClient.newHttpClient();
        httpRequest = HttpRequest.newBuilder()
            .header("Title", msg.getTitle())
            .POST(HttpRequest.BodyPublishers.ofString(msg.getBody()))
            .uri(URI.create(ntfyUrl))
            .build();

        statusCode = httpClient.sendAsync(httpRequest, BodyHandlers.ofString())
        .thenApply(HttpResponse::statusCode)
        .join();
    }

    public String getResponse() {
        return statusCode >= 200 && statusCode < 300 ?
        "Sucessful at " + LocalDateTime.now().toString().substring(0, 16) :
        "NOT sucessful at " + LocalDateTime.now().toString().substring(0, 16);
    }
}
