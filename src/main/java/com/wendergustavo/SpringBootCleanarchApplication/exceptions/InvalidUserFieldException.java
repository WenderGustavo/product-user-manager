package com.wendergustavo.SpringBootCleanarchApplication.exceptions;

public class InvalidUserFieldException extends  RuntimeException{
    public InvalidUserFieldException(String message) {
        super(message);
    }
}
