package com.mentormate.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleApiRequestNotFoundException(NotFoundException e) {
        
        int httpStatus = HttpStatus.NOT_FOUND.value();
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                httpStatus,
                OffsetDateTime.now()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(httpStatus));
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleApiRequestIllegalArgumentException(BadRequestException e) {
        
        int httpStatus = HttpStatus.BAD_REQUEST.value();
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                httpStatus,
                OffsetDateTime.now()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(httpStatus));
    }
    
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleApiRequestValidateObjectException(ValidationException e) {
        
        int httpStatus = HttpStatus.NOT_FOUND.value();
        
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                httpStatus,
                OffsetDateTime.now()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(httpStatus));
    }
}
