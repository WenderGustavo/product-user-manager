package com.wendergustavo.SpringBootCleanarchApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadCredentialsException extends ResponseStatusException {

    public BadCredentialsException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

    public BadCredentialsException(String reason, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, reason, cause);
    }
}
