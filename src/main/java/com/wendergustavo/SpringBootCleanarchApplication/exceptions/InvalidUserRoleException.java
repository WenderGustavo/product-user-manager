package com.wendergustavo.SpringBootCleanarchApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class InvalidUserRoleException extends ResponseStatusException {

    public InvalidUserRoleException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public InvalidUserRoleException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
