package com.andruy.assistant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.andruy.assistant.model.Directory;
import com.andruy.assistant.util.BashHandler;
import com.andruy.assistant.util.Constants;

@Service
public class DirectoryService {
    @Value("${my.programming.directory}")
    String base;

    public String createFolder(Directory name) {
        List<String> output = new BashHandler(new String[] { Constants.MAKE_DIR, base + name.getName() }).startAndReturnOutput();

        // if (!output.isEmpty()) {
        // }
        return output.isEmpty() ? "Created folder " + name.getName() : output.get(0);
    }
}
