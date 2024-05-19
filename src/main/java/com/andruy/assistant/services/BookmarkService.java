package com.andruy.assistant.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.andruy.assistant.models.Bookmark;
import com.andruy.assistant.utils.BookmarkRowMapper;
import com.andruy.assistant.utils.BookmarkMapper;
import com.andruy.assistant.utils.Queries;

@Component
public class BookmarkService {
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

    public List<Bookmark> findAllBookmarks() {
        return jdbcTemplate.query(Queries.SELECT_ALL, new BookmarkRowMapper());
    }

    public List<Bookmark> findBookmarkByReference(String str) {
        if (str.isBlank() || str == null) {
            throw new IllegalStateException("Not found");
        }

        return jdbcTemplate.query(Queries.FIND_BY_REFERENCE, new BookmarkRowMapper(), str);
    }
}
