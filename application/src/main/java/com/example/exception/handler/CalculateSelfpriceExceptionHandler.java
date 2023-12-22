package com.example.exception.handler;

import com.example.exception.CalculateSelfpriceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class CalculateSelfpriceExceptionHandler {
    @ExceptionHandler(value = CalculateSelfpriceException.class)
    public ResponseEntity<Map<String, String>> handleCalculateSelfpriceException(CalculateSelfpriceException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.NO_CONTENT);
    }
}
