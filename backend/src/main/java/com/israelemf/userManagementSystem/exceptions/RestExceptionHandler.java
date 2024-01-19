package com.israelemf.userManagementSystem.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorStructure> genericExceptionHandler(RuntimeException exception) {
        HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorStructure errorStructure = new ErrorStructure(
                LocalDateTime.now(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.name(),
                exception.getMessage());

        return new ResponseEntity<>(errorStructure, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class})
    public ResponseEntity<ErrorStructure> userNotFoundException(RuntimeException exception) {
        HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;
        ErrorStructure errorStructure = new ErrorStructure(
                LocalDateTime.now(),
                FORBIDDEN.value(),
                FORBIDDEN.name(),
                exception.getMessage());

        return new ResponseEntity<>(errorStructure, FORBIDDEN);
    }
}
