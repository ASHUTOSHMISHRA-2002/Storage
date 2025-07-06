package com.ashutosh.order_service.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}


//ResourceNotFoundException is a custom unchecked exception used when a resource like an order or customer isn't found. Since it extends RuntimeException, I don’t need to declare it, and I handle it globally using @ControllerAdvice