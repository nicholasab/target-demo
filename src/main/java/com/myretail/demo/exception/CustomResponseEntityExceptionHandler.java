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

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ExceptionHandlerExceptionResolver {

    /*@ExceptionHandler(MongoTimeoutException.class)
    public ResponseEntity<ApiErrorWrapper> handleProductExcpetion(MongoTimeoutException e) {
        ApiErrorWrapper error = new ApiErrorWrapper();
        error.setErrorCode(HttpStatus.NOT_FOUND);
        error.setErrorMessage(e.getMessage());
        return getResponse(error);
    }*/
    //MethodArgumentNotValidException
    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorWrapper> handleArgumentNotValid(Exception ex) {
        ApiErrorWrapper apiErrorWrapper = new ApiErrorWrapper();
        apiErrorWrapper.setErrorCode(HttpStatus.BAD_REQUEST);

        return getResponse(apiErrorWrapper);
    }*/
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
        /*if (ex.getPath().size() > 0) {
            apiErrorWrapper.addErrorMessage(String.format("Field '%s' expected to be of type '%s', found '%s'", ex.getPath().get(0).getFieldName(), ex.getTargetType().getSimpleName(), ex.getValue()));
        } else {
            apiErrorWrapper.addErrorMessage(String.format("Field '%s' expected to be of type '%s'", ex.getValue(), ex.getTargetType().getSimpleName()));
        }*/
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
