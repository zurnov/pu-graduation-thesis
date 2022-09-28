package com.mentormate.restaurant.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends RuntimeException{
    
    public NotFoundException(String message) {
        super(message);
    }
    
}
