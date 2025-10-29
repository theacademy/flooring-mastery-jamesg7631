package com.mthree.dao;

public class OrderDateInvalidException extends RuntimeException {
    public OrderDateInvalidException(String message) {
        super(message);
    }
    public OrderDateInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
