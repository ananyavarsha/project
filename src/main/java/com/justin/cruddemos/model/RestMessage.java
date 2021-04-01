package com.justin.cruddemos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

import java.util.List;

import org.springframework.http.HttpStatus;

@JsonInclude(Include.NON_NULL)
@Data
public class RestMessage {
    private String message;
    private HttpStatus httpStatus;
    private Integer code;

    public RestMessage() {
        super();
    }

    public RestMessage(String message) {
        this.message = message;
    }

    public RestMessage(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public RestMessage(HttpStatus httpStatus, String message, Throwable ex) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public RestMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public RestMessage(String message, HttpStatus httpStatus, Integer customCode) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.code = customCode;
    }

}
