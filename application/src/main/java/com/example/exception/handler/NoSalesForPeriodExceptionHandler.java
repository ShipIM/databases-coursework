package com.example.exception.handler;

import com.example.exception.NoSalesForPeriodException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class NoSalesForPeriodExceptionHandler {

    @ExceptionHandler(value = NoSalesForPeriodException.class)
    public ResponseEntity<Map<String, String>> handleNoSalesForPeriodException(NoSalesForPeriodException exception) {
        return new ResponseEntity<>(Map.of("errors", exception.getMessage()), HttpStatus.NO_CONTENT);
    }
}
