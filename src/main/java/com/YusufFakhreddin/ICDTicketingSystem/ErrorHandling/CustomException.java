package com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling;

public class CustomException extends RuntimeException{

    // use http code and an error message
    private int httpStatusCode;
    public CustomException(int httpStatusCode, String message){
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode(){return httpStatusCode;}




}
