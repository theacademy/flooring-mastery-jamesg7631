package com.mthree.dao;

public class FlooringMasteryOrderDoesNotExistException extends RuntimeException {
    public FlooringMasteryOrderDoesNotExistException(String message) {
        super(message);
    }

    public FlooringMasteryOrderDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
