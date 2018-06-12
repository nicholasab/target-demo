package com.myretail.demo.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;

@JsonPropertyOrder({"timestamp", "status", "errors"})
public class ApiErrorWrapper {

    @JsonProperty("status")
    private HttpStatus errorStatus;
    private String timestamp;

    @JsonProperty("errors")
    private ArrayList<String> errorMessages = new ArrayList<>();

    public ApiErrorWrapper() {
        this.timestamp = new Date().toString();
    }

    public void setErrorCode(HttpStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    public void addErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
    }

}
