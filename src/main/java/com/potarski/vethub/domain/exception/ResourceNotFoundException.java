package com.potarski.vethub.domain.exception;


// Unchecked-исключение для ситуаций, когда мы не находим что-то по айди
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
