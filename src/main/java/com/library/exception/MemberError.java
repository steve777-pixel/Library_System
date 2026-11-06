package com.library.exception;

public enum MemberError{
    NULL_MEMBER("Member cannot be null."),
    MEMBER_NOT_ACTIVE("Member is not active."),
    MEMBER_SUSPENDED("Member is suspended."),
    BORROWING_NULL_BOOK("Cannot borrow a null book."),
    BORROWING_LIMIT_REACHED("Cannot borrow more than 5 books at a time."),
    BORROWING_DUPLICATE("Member already borrowed this book."),
    RETURNING_NULL_BOOK("Cannot return a null book."),
    INVALID_LOAN("Member does not own this loan."),
    NOT_BORROWED("Member didn't borrow this book.");

    private final String message;

    MemberError(String message){
        this.message = message;
    }

    public String getMessage() {return message;}
}