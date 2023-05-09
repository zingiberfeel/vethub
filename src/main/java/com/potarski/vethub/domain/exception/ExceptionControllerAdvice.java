package com.potarski.vethub.domain.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        ExceptionBody err = new ExceptionBody(e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceMappingException.class)
    public ResponseEntity<Object> handleResourceMappingException(ResourceMappingException e) {
        log.error(e.getMessage());
        ExceptionBody err = new ExceptionBody(e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException e) {
        log.error(e.getMessage());
        ExceptionBody err = new ExceptionBody(e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorAttributes.put("message", "Bad request.");
        errorAttributes.put("detail", "Failed to read request.");
        errorAttributes.put("instance", ex.getParameter().getParameterName());
        return new ResponseEntity<>(errorAttributes, HttpStatus.BAD_REQUEST);
    }

}
