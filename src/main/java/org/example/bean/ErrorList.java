package org.example.bean;

import java.util.Map;

public class ErrorList {
    private int item;
    private Map<String, String> fieldErrors;

    public ErrorList(int item, Map<String, String> fieldErrors) {
        this.item = item;
        this.fieldErrors = fieldErrors;
    }

    public int getItem() {
        return item;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}

