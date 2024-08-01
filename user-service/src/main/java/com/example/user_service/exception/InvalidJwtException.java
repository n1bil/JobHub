package com.example.user_service.exception;

import org.springframework.http.HttpStatus;

public class InvalidJwtException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public InvalidJwtException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public InvalidJwtException(HttpStatus status, String message, String message2) {
        super(message);
        this.status = status;
        this.message = message2;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
