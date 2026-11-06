package com.library.exception.utils;

public enum EmailError{
    NULL_EMAIL("Email cannot be null or blank."),
    INVALID_EMAIL_FORMAT("Invalid email format.");

    private final String message;

    EmailError(String message){
        this.message = message;
    }

    public String getMessage() {return message;}
}