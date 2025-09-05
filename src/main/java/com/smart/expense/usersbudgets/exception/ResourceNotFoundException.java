package com.smart.expense.usersbudgets.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, String field, String value) { 
        super(String.format("%s not found with %s: %s", message, field, value)); 
    }
}
