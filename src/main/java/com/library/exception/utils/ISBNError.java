package com.library.exception.utils;

public enum ISBNError{
    NULL_ISBN("ISBN cannot be null."),
    INVALID_FORMAT("Invalid ISBN-13 format structure! Must be 13 digits starting with 978 or 979."),
    INVALID_CHECK_DIGIT("Invalid ISBN-13 check digit (checksum failed).");

    private final String message;

    ISBNError(String message){
        this.message = message; 
    }

    public String getMessage() {return message;}
}