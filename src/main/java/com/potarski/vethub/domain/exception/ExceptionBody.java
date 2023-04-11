package com.potarski.vethub.domain.exception;

import java.util.Map;

public class ExceptionBody {
    private String message;
    private Map<String, String> errors;

    public ExceptionBody(String message) {
        this.message = message;
    }


}
