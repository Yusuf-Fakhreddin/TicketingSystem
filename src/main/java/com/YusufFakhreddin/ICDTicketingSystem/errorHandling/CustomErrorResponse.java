package com.YusufFakhreddin.ICDTicketingSystem.errorHandling;

import java.util.Map;

public class CustomErrorResponse {
    private int status;
    private String message;

    // map to hold validation errors
    private Map<String, String> errors;

    private long timeStamp;


    public CustomErrorResponse(){}

    public CustomErrorResponse(int status, String message, long timeStamp, Map<String, String> errors){
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.errors = errors;
    }

    public CustomErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
    }

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public Map<String, String> getErrors(){return errors;}
    public void setErrors(Map<String, String> errors){this.errors = errors;}

    public long getTimeStamp(){return timeStamp;}
    public void setTimeStamp(long timeStamp){this.timeStamp = timeStamp;}
}
