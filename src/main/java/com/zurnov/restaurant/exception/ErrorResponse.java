package com.zurnov.restaurant.exception;

import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class ErrorResponse implements Serializable {
    
    private final String message;
    private final int statusCode;
    private final OffsetDateTime timestamp;
    
}
