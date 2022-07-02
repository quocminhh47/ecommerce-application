package com.nashtech.ecommercialwebsite.exceptions;

public class AuthenticationException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

}
