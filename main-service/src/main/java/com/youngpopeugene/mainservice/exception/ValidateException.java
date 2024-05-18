package com.youngpopeugene.mainservice.exception;

public class ValidateException extends RuntimeException {
    public ValidateException() {
        super("Validation error");
    }

    public ValidateException(String message) {
        super(message);
    }
}