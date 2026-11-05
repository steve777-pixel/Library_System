package com.library.exception;

public enum LoanError{
    NULL_LOAN("Loan cannot be null."),
    NULL_DUE_DATE("Due date cannot be null."),
    LOAN_ALREADY_RETURNED("Loan can be returned only once."),
    DUE_DATE_AFTER_RETURN("Due date cannot be modified after returning."),
    PAST_DUE_DATE("Due date cannot be in the past.");

    private final String message;

    LoanError(String message){
        this.message = message;
    }

    public String getMessage() {return message;}
}