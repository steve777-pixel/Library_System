package com.library.domain.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.library.exception.utils.ISBNException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ISBNTest {

    @Test 
    void ISBNCreationSuccess(){
        ISBN isbn = new ISBN("978-1-56619-909-4");

        assertEquals("9781566199094", isbn.getValue());
    }

    @Test 
    void throwWhenIsbnIsNull(){
        ISBNException exception = assertThrows(
            ISBNException.class, 
            () -> new ISBN(null)
        );
        assertEquals("ISBN cannot be null.", exception.getMessage());
    }

    @ParameterizedTest 
    @ValueSource(strings = {
        "", // Blank
        "    ", // Empty
        "123-4-56-789012-3", // Invalid prefix
        "978-014118776", // Incorrect length
        "978-0-15", // Less digits
        "978-0-14-118776-521" // More digits
    })
    void throwWhenIsbnHasInvalidFormat(String invalidString){
        ISBNException exception = assertThrows(
            ISBNException.class, 
            () -> new ISBN(invalidString)
        );
        assertEquals("Invalid ISBN-13 format structure! Must be 13 digits starting with 978 or 979.", exception.getMessage());
    }

    @ParameterizedTest 
    @ValueSource(strings = {
        "978-0-14-118776-5",
        "978-0596528120",
        "978-1788399083"
    })
    void thrownWhenIsbnHasWrongCheckDigit(String invalidString){
        ISBNException exception = assertThrows(
            ISBNException.class, 
            () -> new ISBN(invalidString)
        );
        assertEquals("Invalid ISBN-13 check digit (checksum failed).", exception.getMessage());
    }

    @Test 
    void testEqualsOverride(){
        ISBN isbn_1 = new ISBN("978-1-56619-909-4");
        ISBN isbn_2 = new ISBN("978-1-56619-909-4");

        assertFalse(isbn_1 == isbn_2);
        assertTrue(isbn_1.equals(isbn_2));
    }
}
