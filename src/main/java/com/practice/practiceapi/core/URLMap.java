package com.practice.practiceapi.core;

import com.practice.practiceapi.controllers.URLApiController;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class URLMap {
    private Logger logger = LoggerFactory.getLogger(URLMap.class);

    private HashMap<String, String> urlMap = new HashMap<>();

    public URLMap() {

    }

    public HashMap<String, String> getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(HashMap<String, String> urlMap) {
        this.urlMap = urlMap;
    }

    public void add(String shortUrl, String originalUrl) {

        this.urlMap.put(shortUrl, originalUrl);
        logger.info("Estado del mapa actual: ");
        for (String key : urlMap.keySet()) {
            logger.info(key + ": " + this.urlMap.get(key));
        }
    }

    public String get(String shortUrl) {
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
