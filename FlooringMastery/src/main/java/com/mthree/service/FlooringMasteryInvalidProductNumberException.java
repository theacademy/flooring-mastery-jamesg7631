package com.mthree.service;

public class FlooringMasteryInvalidProductNumberException extends RuntimeException {
    public FlooringMasteryInvalidProductNumberException(String message) {
        super(message);
    }
    public FlooringMasteryInvalidProductNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
