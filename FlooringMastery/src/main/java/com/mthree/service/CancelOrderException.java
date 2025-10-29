package com.mthree.service;

public class CancelOrderException extends RuntimeException {
    public CancelOrderException(String message) {
        super(message);
    }
    public CancelOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
