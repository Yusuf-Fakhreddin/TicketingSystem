package com.YusufFakhreddin.TicketingSystem.errorHandling;

import java.util.Map;

public class CustomException extends RuntimeException{

    // use http code and an error message
    private int httpStatusCode;
    private Map<String, String> errors;

    public CustomException(int httpStatusCode, String message){
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public CustomException(int httpStatusCode, String message, Map<String, String> errors){
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.errors = errors;
    }

    public int getHttpStatusCode(){return httpStatusCode;}

    public Map<String, String> getErrors(){return errors;}




}
