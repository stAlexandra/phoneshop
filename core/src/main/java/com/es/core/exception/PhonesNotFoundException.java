package com.es.core.exception;

public class PhonesNotFoundException extends ItemNotFoundException {
    public PhonesNotFoundException(Long phoneId) {
        super(phoneId.toString());
    }
}
