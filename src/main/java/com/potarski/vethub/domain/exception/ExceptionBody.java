package com.potarski.vethub.domain.exception;

import lombok.Data;

import java.util.Map;

@Data
public class ExceptionBody {
    private String message;
    private Map<String, String> errors;

    public ExceptionBody(String message) {
        this.message = message;
    }


}
