package com.example.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException exception) {
        Map<String, List<String>> body = new HashMap<>();
        body.put("errors",
                exception.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isBlank())
                        .toList());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolationException
            (ConstraintViolationException ex) {
        Map<String, List<String>> body = new HashMap<>();
        body.put("errors",
                ex.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isBlank())
                        .toList());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}