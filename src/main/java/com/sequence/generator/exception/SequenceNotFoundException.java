package com.sequence.generator.exception;


public class SequenceNotFoundException extends RuntimeException {

    public SequenceNotFoundException(String message) {
        super(message);
    }

    public SequenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}