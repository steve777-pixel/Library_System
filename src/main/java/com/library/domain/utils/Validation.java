package com.library.domain.utils;

public class Validation{
    public static String validateNotBlank(String value, String fieldName){
        if(value.isBlank()){
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value;
    }

    public static Integer validateNotNegative(Integer value, String fieldName){
        if(value == null){
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
        if(value < 0){
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
        return value;
    }
}