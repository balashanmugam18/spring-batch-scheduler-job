package com.project.scheduler.exception;

import com.project.scheduler.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.time.Instant;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleError(IllegalArgumentException ex, WebRequest request){
        ErrorResponse errorResponse = ErrorResponse.builder().instance(request.getDescription(false)).message(ex.getMessage()).detail(ex.getLocalizedMessage()).timestamp(Timestamp.from(Instant.now())).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<?> handleError(DataValidationException ex, WebRequest request){
        ErrorResponse errorResponse = ErrorResponse.builder().instance(request.getDescription(false)).message(ex.getMessage()).detail(ex.getLocalizedMessage()).timestamp(Timestamp.from(Instant.now())).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
