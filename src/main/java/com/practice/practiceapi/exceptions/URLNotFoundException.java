package com.practice.practiceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class URLNotFoundException extends Exception{

    public URLNotFoundException() {
    }

    public URLNotFoundException(String message) {
        super(message);
    }
}
