package com.mthree.service;

public class InvalidCustomerNameException extends RuntimeException {
    public InvalidCustomerNameException(String message) {
        super(message);
    }
    public InvalidCustomerNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
