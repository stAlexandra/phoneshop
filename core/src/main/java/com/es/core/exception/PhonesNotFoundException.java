package com.es.core.exception;

public class PhonesNotFoundException extends RuntimeException {
    public PhonesNotFoundException() {
        super();
    }
    public PhonesNotFoundException(Long phoneId) {
        super("Could not find phone with ID "+ phoneId);
    }
}
