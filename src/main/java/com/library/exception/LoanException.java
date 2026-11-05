package com.library.exception;

public class LoanException extends RuntimeException{
    public LoanException(LoanError error){
        super(error.getMessage());
    }

    public LoanException(LoanError error, String message){
        super(message);
    }

    public LoanException(LoanError error, Throwable cause){
        super(error.getMessage(), cause);
    }
}