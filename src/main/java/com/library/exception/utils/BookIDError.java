package com.library.exception.utils;

public enum BookIDError{
    NULL_BOOK_ID("Book ID cannot be null."),
    INVALID_FORMAT("Invalid book ID format structure. Valid format: AA0000000");

    private final String message;

    BookIDError(String message){
        this.message = message;
    }

    public String getMessage() {return message;}
}