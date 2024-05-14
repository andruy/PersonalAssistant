package com.andruy.assistant.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andruy.assistant.models.Bookmark;
import com.andruy.assistant.services.BookmarkService;

@RestController
public class BookmarkController {
    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/allbookmarks")
    public List<Bookmark> findAllBookmarksHttp() {
        return bookmarkService.findAllBookmarksHttp();
    }

    @GetMapping("/bookmark")
    public List<Bookmark> findAllBookmarks() {
        return bookmarkService.findAllBookmarks();
    }

    @GetMapping("/bookmarks")
    public List<Bookmark> findBookmarkByReference(@RequestParam(defaultValue = "") String str) {
        return bookmarkService.findBookmarkByReference(str);
    }
}
