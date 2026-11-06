package com.library.exception.utils;

public class BookIDException extends RuntimeException{
    public BookIDException(BookIDError error){
        super(error.getMessage());
    }
}