package com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

//    handle custom thrown exception
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleException(CustomException exc){
        CustomErrorResponse error = new CustomErrorResponse();
        error.setStatus(exc.getHttpStatusCode());
        error.setMessage(exc.getMessage());
        error.setErrors(exc.getErrors());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }

}