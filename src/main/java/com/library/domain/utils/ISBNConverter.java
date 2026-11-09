package com.library.domain.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ISBNConverter implements AttributeConverter<ISBN, String>{
    @Override
    public String convertToDatabaseColumn(ISBN value){
        return value == null ? null : value.getValue();
    }

    @Override 
    public ISBN convertToEntityAttribute(String value){
        return value == null ? null : new ISBN(value);
    }
}