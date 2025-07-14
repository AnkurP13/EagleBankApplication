package org.example.bean;

import java.util.List;

public class ErrorResponse {
    private List<ErrorList> errors;

    public ErrorResponse(List<ErrorList> errors) {
        this.errors = errors;
    }

    public List<ErrorList> getErrors() {
        return errors;
    }
}

