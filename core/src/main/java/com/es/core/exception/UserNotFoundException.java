package com.es.core.exception;

public class UserNotFoundException extends ItemNotFoundException{
    public UserNotFoundException(String id) {
        super("user", id);
    }
}
