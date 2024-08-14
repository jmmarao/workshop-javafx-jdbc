package com.jmmarao.workshopjavafxjdbc.models.exceptions;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4761443611489116812L;

    private Map<String, String> errors = new HashMap<>();

    public ValidationException(String message) {
        super(message);
    }

    public void addError(String fieldName, String errorMessage) {
        errors.put(fieldName, errorMessage);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
