package com.andruy.assistant.util;

import java.util.ArrayList;
import java.util.List;

import com.andruy.assistant.model.Directory;

public class DirectoryList {
    private List<Directory> directories = new ArrayList<>();

    public DirectoryList() {
        BashHandler bash = new BashHandler(new String[] { Constants.LIST, System.getProperty("programmingDirectory") });
        List<String> list = bash.startAndReturnOutput();

        for (String s : list) {
            directories.add(new Directory(s));
        }
    }

    public List<Directory> getDirectories() {
        return directories;
    }
}
