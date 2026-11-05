package com.library.exception.utils;

public class ISBNException extends RuntimeException{
    public ISBNException(ISBNError error){
        super(error.getMessage());
    }

    public ISBNException(ISBNError error, String message){
        super(message);
    }

    public ISBNException(ISBNError error, Throwable cause){
        super(error.getMessage(), cause);
    }
}