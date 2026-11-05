package com.library.domain.utils;

public enum MemberStatus{
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    EXPIRED("Expired");

    private final String description;

    MemberStatus(String description){
        this.description = description;
    }

    public String getDescription() {return description;}
}