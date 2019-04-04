package com.es.core.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super();
    }
    public ItemNotFoundException(Long id) {
        super("Could not find item by id = " + id);
    }
}
