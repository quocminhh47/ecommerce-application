package com.nashtech.ecommercialwebsite.exceptions;

public class UnsupportedMediaException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UnsupportedMediaException() {
        super();
    }

    public UnsupportedMediaException(String message) {
        super(message);
    }

    public UnsupportedMediaException(String message, Throwable cause) {
        super(message, cause);
    }

}
