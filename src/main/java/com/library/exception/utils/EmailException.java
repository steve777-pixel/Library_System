package com.library.exception.utils;

public class EmailException extends RuntimeException{
    public EmailException(EmailError error){
        super(error.getMessage());
    }
}