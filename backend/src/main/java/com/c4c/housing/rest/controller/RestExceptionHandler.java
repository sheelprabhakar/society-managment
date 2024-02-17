package com.c4c.housing.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Rest exception handler.
 */
@ControllerAdvice
public final class RestExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handle bad credential exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialException(
           final BadCredentialsException ex, final WebRequest request) {

        return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
    }
}
