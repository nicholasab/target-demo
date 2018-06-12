package com.myretail.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductMismatchException extends RuntimeException {
    public ProductMismatchException(String message) {
        super(message);
    }
}
