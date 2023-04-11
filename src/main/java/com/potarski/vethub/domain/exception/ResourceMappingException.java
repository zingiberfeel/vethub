package com.potarski.vethub.domain.exception;

// Unchecked-иключение для ошибок в JDBC
public class ResourceMappingException extends RuntimeException{
    public ResourceMappingException(String message) {
        super(message);
    }
}
