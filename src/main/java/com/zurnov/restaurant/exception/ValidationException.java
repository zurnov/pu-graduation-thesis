package com.zurnov.restaurant.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationException extends RuntimeException {

    private final List<String> errors;

    public ValidationException(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return String.join("/n", errors);
    }
}
