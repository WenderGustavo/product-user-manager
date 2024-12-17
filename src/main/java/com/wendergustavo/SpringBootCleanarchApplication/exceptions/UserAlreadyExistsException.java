package com.wendergustavo.SpringBootCleanarchApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {

    public UserAlreadyExistsException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }

    public UserAlreadyExistsException(String reason, Throwable cause) {
        super(HttpStatus.CONFLICT, reason, cause);
    }
}
