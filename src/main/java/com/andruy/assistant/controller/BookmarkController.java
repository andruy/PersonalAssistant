package com.andruy.assistant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andruy.assistant.model.Bookmark;
import com.andruy.assistant.service.BookmarkService;

@RestController
public class BookmarkController {
    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/allbookmarks")
    public List<Bookmark> findAllBookmarksHttp() {
        return bookmarkService.findAllBookmarksHttp();
    }

    @GetMapping("/bookmark")
    public List<String> findAllBookmarks() {
        return bookmarkService.findAllBookmarks();
    }

    @GetMapping("/bookmarks")
    public List<Bookmark> findBookmarkByReference(@RequestParam(defaultValue = "") String str) {
        return bookmarkService.findBookmarkByReference(str);
    }

    @GetMapping("/logReader")
    public ResponseEntity<Map<String, String>> logReader() {
        return ResponseEntity.ok(bookmarkService.logReader());
    }
}
