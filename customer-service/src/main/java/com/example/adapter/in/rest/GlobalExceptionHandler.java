package com.example.adapter.in.rest;

import com.example.adapter.in.rest.dto.ErrorResponse;
import com.example.core.domain.exception.CustomerNotFoundException;
import com.example.core.domain.exception.DomainConflictException;
import com.example.core.domain.exception.DomainException;
import com.example.core.domain.exception.DomainValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ErrorResponse> handleException(DomainValidationException e) {
        log.warn("Domain validation exception: {}", e.getMessage(), e);
        return badRequest(e);
    }

    @ExceptionHandler(DomainConflictException.class)
    public ResponseEntity<ErrorResponse> handleException(DomainConflictException e) {
        log.warn("Domain conflict exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.of(HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomerNotFoundException e) {
        log.warn("Customer not found exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleException(DomainException e) {
        log.error("Domain exception: {}", e.getMessage(), e);
        return badRequest(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Internal service error"));
    }

    private ResponseEntity<ErrorResponse> badRequest(Exception e) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
