package com.nttdata.products.api.exception;

public class ProductServiceException extends RuntimeException {
    public ProductServiceException(String message) {
        super(message);
    }
}
