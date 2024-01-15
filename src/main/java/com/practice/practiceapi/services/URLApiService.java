package com.practice.practiceapi.services;

import com.practice.practiceapi.Repositories.URLRepository;
import com.practice.practiceapi.core.Client;
import com.practice.practiceapi.core.URLMap;
import com.practice.practiceapi.exceptions.IncorrectURLException;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Clase que contendrá exclusivamente los servicios para la API de recorte de url.
 *
 */
@Service
public class URLApiService {
    private final String BASE_URL = "nicoapp.com/";
    private Integer urlCounter = 0;

    private Logger logger = LoggerFactory.getLogger(URLApiService.class);
    private URLMap urlMap;
    private URLRepository repository;

    @Autowired
    public URLApiService(URLMap urlMap, URLRepository repository) {
        this.urlMap = urlMap;
        this.repository = repository;
    }


    public String shorten(String originalUrl) throws IncorrectURLException{

        if (isUrl(originalUrl)){
            var shortUrl = cut(originalUrl);
            var jsonPair = new JSONObject()
                    .put("key", shortUrl)
                    .put("value", originalUrl);

            repository.insertInDB(jsonPair);
            urlMap.add(shortUrl, originalUrl);
            return shortUrl;
        } else {
            throw new IncorrectURLException("El formato de la URL ingresada no es el correcto");
        }

    }

    public String expand(String shortUrl) {
        String expandedUrl = urlMap.get(shortUrl);

        // En caso de no tener la url guardada en cache, la buscamos en base de datos
        if (expandedUrl == null) {
            expandedUrl = repository.find(shortUrl);

            // Si el resultado de búsqueda en db no es nulo, lo cacheamos para futuras consultas
            if (expandedUrl != null){
                urlMap.add(shortUrl, expandedUrl);
            }
        }
        return expandedUrl;
    }

    private boolean isUrl(String url) {
        var hasProtocol = url.startsWith("http://") || url.startsWith("https://");
        var hasW3 = url.contains("www.");
        //TODO: de momento toma el dominio válido de .com. Se debe refactorizar para que tome otros dominios existentes
        var hasDomain = url.contains(".com");

        return hasProtocol && hasW3 && hasDomain;
    }

    private String cut(String originalUrl) {

        /*
         * Usamos un contador de url que se agregará como prefijo. De esta manera, aunque
         * el algoritmo de hashing devuelva el mismo valor, el prefijo será distinto y se
         * evita cualquier tipo de colisiones.
         */
        urlCounter ++;

        // Uso DigestUtils de ApacheCommons para realizar el hashing
        var hashedUrl = DigestUtils.sha256Hex(originalUrl);

        return BASE_URL + urlCounter.toString() + hashedUrl;
    }

    public JSONObject all() {
        return this.urlMap.getAll();
    }
}
