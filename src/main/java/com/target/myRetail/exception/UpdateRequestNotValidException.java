package com.target.myRetail.exception;

public class UpdateRequestNotValidException extends RuntimeException {
    public UpdateRequestNotValidException(String message) {
        super(message);
    }
}
