package com.library.exception;

public class MemberException extends RuntimeException{
    public MemberException(MemberError error){
        super(error.getMessage());
    }

    public MemberException(MemberError error, String message){
        super(message);
    }

    public MemberException(MemberError error, Throwable cause){
        super(error.getMessage(), cause);
    }
}