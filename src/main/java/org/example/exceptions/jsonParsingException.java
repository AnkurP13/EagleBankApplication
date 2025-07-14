package org.example.exceptions;

public class jsonParsingException extends RuntimeException {
    public jsonParsingException(String message) {
        super(message);
    }
}
