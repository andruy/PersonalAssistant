package com.andruy.assistant.service;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.andruy.assistant.model.Bookmark;
import com.andruy.assistant.util.BookmarkRowMapper;
import com.andruy.assistant.util.BookmarkMapper;
import com.andruy.assistant.util.Queries;

@Service
public class BookmarkService {
    Logger logger = LoggerFactory.getLogger(BookmarkService.class);
    @Value("${my.supabase.key}")
    private String apiKey;
    @Value("${my.supabase.url.get.bookmarks}")
    private String getBookmarks;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BookmarkMapper parser;
    private HttpClient httpClient;
    private HttpRequest httpRequest;

    public List<Bookmark> findAllBookmarksHttp() {
        httpClient = HttpClient.newHttpClient();

        httpRequest = HttpRequest.newBuilder()
            .header("apikey", apiKey)
            .header("Authorization", "Bearer " + apiKey)
            .uri(URI.create(getBookmarks))
            .build();

        String response = httpClient.sendAsync(httpRequest, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join();

        return parser.toBookmarkList(response);
    }

    public List<String> findAllBookmarks() {
        return jdbcTemplate.queryForList("SELECT current_timestamp - pg_postmaster_start_time()", String.class);
    }

    public List<Bookmark> findBookmarkByReference(String str) {
        if (str.isBlank() || str == null) {
            throw new IllegalStateException("Not found");
        }

        return jdbcTemplate.query(Queries.FIND_BY_REFERENCE, new BookmarkRowMapper(), str);
    }

    public Map<String, String> logReader() {
        StringBuilder sb = new StringBuilder();
        File log = new File("logs/app.log");

        try {
            Scanner scanner = new Scanner(log);
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return Map.of("logs", sb.toString());
    }
}
