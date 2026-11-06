package com.library.domain.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.library.exception.utils.BookIDException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookIDTest {
    
    @Test 
    void BookIDCreationSuccess(){
        BookID id = new BookID("AD9009103");
        assertEquals("AD9009103", id.getValue());
    }

    @Test 
    void shouldThrowWhenIdIsNull(){
        BookIDException exception = assertThrows(
            BookIDException.class, 
            () -> new BookID(null)
        );
        assertEquals("Book ID cannot be null.", exception.getMessage());
    }

    @ParameterizedTest 
    @ValueSource(strings = {
        "",
        "   ",
        "AD12345",
        "AD12345678",
        "1234567",
        "A1209345",
        "ABC09190292",
        "AA-1234567",
        "@*#@)*#)@!"
    })
    void shouldThrowWhenIdHasInvalidFormat(String invalidID){
        BookIDException exception = assertThrows(
            BookIDException.class,
            () -> new BookID(invalidID)
        );

        assertEquals("Invalid book ID format structure. Valid format: AA0000000", exception.getMessage());
    }   

    @Test 
    void testEqualsOverride(){
        BookID id_1 = new BookID("CD1012137");
        BookID id_2 = new BookID("CD1012137");

        assertFalse(id_1 == id_2);
        assertTrue(id_1.equals(id_2));
    }
}
