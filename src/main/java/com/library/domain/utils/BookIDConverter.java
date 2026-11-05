package com.library.domain.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class BookIDConverter implements AttributeConverter<BookID, String>{
    @Override 
    public String convertToDatabaseColumn(BookID attribute){
        return attribute == null ? null : attribute.getValue();
    }

    @Override 
    public BookID convertToEntityAttribute(String dbData){
        return dbData == null ? null : new BookID(dbData);
    }
}