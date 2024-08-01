package com.example.user_service.exception;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public APIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}