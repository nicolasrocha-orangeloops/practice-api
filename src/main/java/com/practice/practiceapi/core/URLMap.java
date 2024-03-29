package com.practice.practiceapi.core;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class URLMap {

    private HashMap<String, String> urlMap = new HashMap<>();

    public HashMap<String, String> getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(HashMap<String, String> urlMap) {
        this.urlMap = urlMap;
    }

    public void add(String shortUrl, String originalUrl) {

        this.urlMap.put(shortUrl, originalUrl);
    }

    public String getOriginalUrl(String shortUrl) {
        return this.urlMap.get(shortUrl);
    }


    public JSONObject getAll() {
        var result = new JSONObject();

        for (String key : urlMap.keySet()) {
            result.put(key, urlMap.get(key));
        }
        return result;
    }
}
