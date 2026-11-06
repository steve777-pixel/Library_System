package com.library.exception;

public enum BookError{
    NULL_BOOK("Book cannot be null."),
    NULL_COPIES("The number of copies cannot be null."),
    NEGATIVE_COPIES("The number of copies cannot be negative."),
    COPIES_OUT_OF_RANGE("Not enough copies."),
    TOTAL_COPIES_EXCEEDED("Available copies cannot exceed the number of total copies."),
    BOOK_NOT_AVAILABLE("Book has no available copies.");

    private final String message;

    BookError(String message){
        this.message = message;
    }

    public String getMessage() {return message;}
}