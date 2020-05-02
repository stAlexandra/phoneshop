package com.es.core.exception;

public class DuplicateDiscountException extends RuntimeException {
    public DuplicateDiscountException(Throwable cause) {
        super(cause);
    }

    public DuplicateDiscountException(String message, Throwable cause) {
        super(message, cause);
    }
}
