package com.practice.practiceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncorrectURLException extends Exception{

    public IncorrectURLException() {
    }

    public IncorrectURLException(String message) {
        super(message);
    }
}
