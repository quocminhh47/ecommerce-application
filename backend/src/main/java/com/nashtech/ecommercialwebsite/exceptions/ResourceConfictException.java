package com.nashtech.ecommercialwebsite.exceptions;

public class ResourceConfictException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResourceConfictException() {
        super();
    }

    public ResourceConfictException(String message) {
        super(message);
    }

    public ResourceConfictException(String message, Throwable cause) {
        super(message, cause);
    }

}
