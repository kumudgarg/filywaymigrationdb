package com.thoughtworks.flywayDbMigration.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class UserNotPresentException extends RuntimeException {
    private  HttpStatus statusCode;

    public UserNotPresentException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
