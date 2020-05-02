package com.es.core.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super();
    }
    public ItemNotFoundException(String id) {
        super("Could not find item by id = " + id);
    }
    public ItemNotFoundException(String itemType, String id) {
        super("Could not find " + itemType + " by id = " + id);
    }
}
