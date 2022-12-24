package com.observationclass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NoSuchElementException extends RuntimeException {
    private static final long serialVersionUID = -8014749955090725783L;

    public NoSuchElementException(Exception exception) {
        super(exception);
    }
}
