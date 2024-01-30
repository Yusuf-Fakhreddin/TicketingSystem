package com.YusufFakhreddin.ICDTicketingSystem.response;

public class ApiResopnse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResopnse(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public T getData(){return data;}
    public void setData(T data){this.data = data;}
}
