package com.andruy.assistant.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.andruy.assistant.model.Bookmark;

public class BookmarkRowMapper implements RowMapper<Bookmark> {
    @Override
    @Nullable
    public Bookmark mapRow(@NonNull ResultSet resultSet, int rowNum) throws SQLException {
        return new Bookmark(
            resultSet.getString("Reference"),
            resultSet.getString("Link"),
            LocalDate.parse(resultSet.getString("Date")),
            resultSet.getInt("Id")
        );
    }
}
