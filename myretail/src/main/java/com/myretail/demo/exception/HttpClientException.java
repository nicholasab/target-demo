package com.myretail.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HttpClientException extends ResponseStatusException {

    public HttpClientException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
