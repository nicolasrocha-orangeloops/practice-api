package com.practice.practiceapi.controllers;


import com.practice.practiceapi.exceptions.IncorrectURLException;
import com.practice.practiceapi.exceptions.URLNotFoundException;
import com.practice.practiceapi.services.URLApiService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/*
 * Clase que contendrá exclusivamente los controladores para la API de recorte de url.
 *
 */
@RestController
@RequestMapping("/url-shortener")
public class URLApiController {
    private Logger logger = LoggerFactory.getLogger(URLApiController.class);
    private URLApiService urlApiService;

    @Autowired
    public URLApiController(URLApiService urlApiService) {
        this.urlApiService = urlApiService;
    }

    @RequestMapping(value = "/shorten", method = RequestMethod.POST)
    @ResponseBody
    public String shorten(@RequestBody String requestString) throws IncorrectURLException {
        var requestBody = new JSONObject(requestString);
        var originalUri = requestBody.getString("originalUrl");

        return urlApiService.shorten(originalUri);
    }

    @RequestMapping(value = "/expand", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> expand(
            @RequestParam(defaultValue = "false") Boolean getObject,
            @RequestBody String requestString
    ) throws URLNotFoundException {
        var requestBody = new JSONObject(requestString);
        var shortUrl = requestBody.getString("shortUrl");
        var originalUrl = urlApiService.expand(shortUrl);

        if (originalUrl == null) {
            throw new URLNotFoundException("La URL no se encuentra en la base de datos");
        }

        if (getObject) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(originalUrl);

        } else {
            return redirect(originalUrl);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public String show() {
        return urlApiService.all().toString();
    }

    private ResponseEntity<String> redirect(String redirectionUrl) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectionUrl))
                .build();
    }

}
