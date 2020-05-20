package com.es.core.exception;

public class ProductsNotFoundException extends ItemNotFoundException {
    public ProductsNotFoundException(Long id) {
        super(id.toString());
    }
}
