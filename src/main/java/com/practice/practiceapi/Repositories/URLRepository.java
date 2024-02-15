package com.practice.practiceapi.Repositories;

import com.practice.practiceapi.core.Client;
import com.practice.practiceapi.core.URLMap;
import com.practice.practiceapi.services.URLApiService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class URLRepository {
    private Logger logger = LoggerFactory.getLogger(URLApiService.class);
    private Client client;
    private final URLMap urlMap;

    @Autowired
    public URLRepository(URLMap urlMap, Client client) {
        this.urlMap = urlMap;
        this.client = client;
    }

    @Async
    public void insertInDB(JSONObject object) {
        var shortUrl = object.get("key");
        var longUrl = object.getString("value");
        var query = String.format("INSERT INTO urls (url_short, url_long) VALUES (\'%s\', \'%s\')", shortUrl, longUrl);
        var shortUrl = object.getString("key");
        var originalUrl = object.getString("value");
        var query = String.format("INSERT INTO urls (url_short, url_long) VALUES (\'%s\', \'%s\')", shortUrl, originalUrl);
        logger.debug(query);

        try (Statement statement = client.con.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("No se pudo insertar el objeto en la base de datos.");
            e.printStackTrace();
        }

        urlMap.add(shortUrl, originalUrl);

    }

    public String find(String shortUrl) {
        var query = String.format("SELECT url_long FROM urls WHERE url_short = \'%s\'", shortUrl);
        String expandedUrl = null;
        logger.debug(query);


        try (Statement statement = client.con.createStatement()) {
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                expandedUrl = result.getString("url_long");
            }

        } catch (SQLException e) {
            logger.error("No se pudo obtener el objeto de la base de datos.");
            e.printStackTrace();
        }

        return expandedUrl;
    }

    public String findShortByOriginal(String originalUrl) {
        var query = String.format("SELECT url_short FROM urls WHERE url_long = \'%s\'", originalUrl);
        String shortUrl = null;

        for (String key : urlMap.getUrlMap().keySet()) {

            if (urlMap.getOriginalUrl(key).equals(originalUrl)) {
                logger.info(String.format("Enlace a %s recuperado desde mapa local", originalUrl));
                return key;
            }
        }

        if (shortUrl == null) {
            try (Statement statement = client.con.createStatement()) {
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    shortUrl = result.getString("url_short");
                    urlMap.add(shortUrl, originalUrl);
                    logger.info(String.format("Enlace a %s recuperado desde base de datos", originalUrl));

                }
            } catch (SQLException e) {
                logger.error("No se pudo obtener el objeto de la base de datos.");
                e.printStackTrace();
            }
        }

        return shortUrl;
    }
}
