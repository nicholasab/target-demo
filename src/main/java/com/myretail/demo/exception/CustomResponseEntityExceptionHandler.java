package com.myretail.demo.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ExceptionHandlerExceptionResolver {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorWrapper> handleArgumentNotValid(MethodArgumentNotValidException ex) {
        ApiErrorWrapper apiErrorWrapper = new ApiErrorWrapper();
        apiErrorWrapper.setErrorCode(HttpStatus.BAD_REQUEST);
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            apiErrorWrapper.addErrorMessage(String.format("Field error: %s", error.getDefaultMessage()));
        }
        return getResponse(apiErrorWrapper);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiErrorWrapper> handleInvalidFormat(InvalidFormatException ex) {
        ApiErrorWrapper apiErrorWrapper = new ApiErrorWrapper();
        apiErrorWrapper.setErrorCode(HttpStatus.BAD_REQUEST);
        apiErrorWrapper.addErrorMessage(ex.getOriginalMessage());
        return getResponse(apiErrorWrapper);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ApiErrorWrapper> handleDataResourceFailure(DataAccessResourceFailureException ex) {
        ApiErrorWrapper apiErrorWrapper = new ApiErrorWrapper();
        apiErrorWrapper.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        apiErrorWrapper.addErrorMessage("Error in database communication");
        return getResponse(apiErrorWrapper);
    }


    private ResponseEntity<ApiErrorWrapper> getResponse(ApiErrorWrapper wrapper) {
        return new ResponseEntity<>(wrapper, wrapper.getErrorStatus());
    }
}
