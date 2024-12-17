package com.wendergustavo.SpringBootCleanarchApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidArgumentDomainException extends ResponseStatusException {

    public InvalidArgumentDomainException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public InvalidArgumentDomainException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
