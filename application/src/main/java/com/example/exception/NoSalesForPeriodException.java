package com.example.exception;

public class NoSalesForPeriodException extends RuntimeException {
    public NoSalesForPeriodException(String message) {
        super(message);
    }
}
