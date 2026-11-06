package com.library.domain.utils;

import java.util.Objects;
import java.util.regex.Pattern;
import com.library.exception.utils.BookIDError;
import com.library.exception.utils.BookIDException;

// public record ???
public final class BookID{
    //AA0000000 (2 Capital letters | 7 digits)
    private static final Pattern BOOK_ID_PATTERN = 
        Pattern.compile("^[A-Z]{2}\\d{7}$");

    private final String value;

    public BookID(String id){
        if(id == null) throw new BookIDException(BookIDError.NULL_BOOK_ID);
        String trimmed = id.trim();

        if(!BOOK_ID_PATTERN.matcher(trimmed).matches()){
            throw new BookIDException(BookIDError.INVALID_FORMAT);
        }

        this.value = trimmed;
    }

    public String getValue(){
        return value;
    }

    @Override 
    public String toString(){
        return value;
    }

    @Override 
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        BookID other = (BookID) obj;
        return Objects.equals(value, other.value);
    }

    @Override 
    public int hashCode(){
        return Objects.hash(value);
    }
}