package com.myretail.demo.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * Custom exception handler to catch and format non-human readable
 * exceptions such as deserialization exceptions and to strive for
 * consistent error messages
 */
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ExceptionHandlerExceptionResolver {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(HttpClientException.class)
    public ResponseEntity<ApiErrorWrapper> handleHttpClientException(HttpClientException ex) {
        ApiErrorWrapper apiErrorWrapper = new ApiErrorWrapper();
        apiErrorWrapper.setErrorCode(ex.getStatus());
        apiErrorWrapper.addErrorMessage(ex.getMessage());
        log.debug(apiErrorWrapper.toString());
        return getResponse(apiErrorWrapper);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorWrapper> handleArgumentNotValid(MethodArgumentNotValidException ex) {
        ApiErrorWrapper apiErrorWrapper = new ApiErrorWrapper();
        apiErrorWrapper.setErrorCode(HttpStatus.BAD_REQUEST);
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            apiErrorWrapper.addErrorMessage(String.format("Field error: %s", error.getDefaultMessage()));
        }
        log.debug(apiErrorWrapper.toString());
        return getResponse(apiErrorWrapper);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiErrorWrapper> handleInvalidFormat(InvalidFormatException ex) {
        ApiErrorWrapper apiErrorWrapper = new ApiErrorWrapper();
        apiErrorWrapper.setErrorCode(HttpStatus.BAD_REQUEST);
        apiErrorWrapper.addErrorMessage(ex.getOriginalMessage());
        log.debug(apiErrorWrapper.toString());
        return getResponse(apiErrorWrapper);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ApiErrorWrapper> handleDataResourceFailure(DataAccessResourceFailureException ex) {
        ApiErrorWrapper apiErrorWrapper = new ApiErrorWrapper();
        apiErrorWrapper.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        apiErrorWrapper.addErrorMessage("Error in database communication");
        log.debug(apiErrorWrapper.toString());
        return getResponse(apiErrorWrapper);
    }


    private ResponseEntity<ApiErrorWrapper> getResponse(ApiErrorWrapper wrapper) {
        return new ResponseEntity<>(wrapper, wrapper.getErrorStatus());
    }
}
