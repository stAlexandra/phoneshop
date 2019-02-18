package com.es.core.exception;

public class PhoneNotFoundException extends RuntimeException {
    public PhoneNotFoundException() {
        super();
    }

    public PhoneNotFoundException(Long phoneId) {
        super("Could not find phone with ID "+ phoneId);
    }
}
