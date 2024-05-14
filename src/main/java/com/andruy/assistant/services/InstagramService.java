package com.andruy.assistant.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class InstagramService {
    private List<String> followers;
    private List<String> following;
    private StringBuilder sb;

    public List<List<String>> getList(Map<String, String> lists) {
        followers = populate(lists.get("followers"));
        following = populate(lists.get("following"));

        return List.of(followers, following, notMyFollowers());
    }

    private List<String> populate(String str) {
        sb = new StringBuilder();
        List<String> list = new ArrayList<>();

        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 'd') {
                if (charArray[++i] == 'i') {
                    if (charArray[++i] == 'r') {
                        if (charArray[++i] == '=') {
                            if (charArray[++i] == '\"') {
                                if (charArray[++i] == 'a') {
                                    if (charArray[++i] == 'u') {
                                        if (charArray[++i] == 't') {
                                            if (charArray[++i] == 'o') {
                                                if (charArray[++i] == '\"') {
                                                    if (charArray[++i] == '>') {
                                                        while (charArray[++i] != '<') {
                                                            sb.append(charArray[i]);
                                                        }
                                                        list.add(sb.toString());
                                                        sb = new StringBuilder();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    private List<String> notMyFollowers() {
        return following.stream().filter(i -> !followers.contains(i)).collect(Collectors.toList());
   }
}
