package com.library.exception;

public class BookException extends RuntimeException{
    public BookException(BookError bookError){
        super(bookError.getMessage());
    }

    public BookException(BookError bookError, String message){
        super(message);
    }

    public BookException(BookError bookError, Throwable cause){
        super(bookError.getMessage(), cause);
    }
}