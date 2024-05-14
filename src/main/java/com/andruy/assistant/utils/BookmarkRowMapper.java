package com.andruy.assistant.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.andruy.assistant.models.Bookmark;

public class BookmarkRowMapper implements RowMapper<Bookmark> {
    @Override
    @Nullable
    public Bookmark mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Bookmark(
            resultSet.getString("Reference"),
            resultSet.getString("Link"),
            LocalDate.parse(resultSet.getString("Date")),
            resultSet.getInt("Id")
        );
    }
}
