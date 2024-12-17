package com.wendergustavo.SpringBootCleanarchApplication.exceptions;

public class LoginAlreadyExistsException extends  RuntimeException{
    public LoginAlreadyExistsException(String message) {
        super(message);
    }
}


