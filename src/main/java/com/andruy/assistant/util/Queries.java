package com.andruy.assistant.util;

public interface Queries {
    String SELECT_ALL = "SELECT * FROM public.bookmarks";
    
    String FIND_BY_REFERENCE = "SELECT * FROM public.bookmarks WHERE reference = ?";

    String FIND_LIKE_REFERENCE = "SELECT * FROM public.bookmarks WHERE reference LIKE ?";
}
