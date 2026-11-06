package com.library.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.library.exception.utils.EmailException;

public class EmailTest {
    
    @Test 
    void emailCreationSuccess(){
        Email email = new Email("stevewatashi111@gmail.com");

        assertEquals("stevewatashi111@gmail.com", email.value());
    }

    @Test 
    void throwsWhenEmailIsNull(){
        EmailException exception = assertThrows(
            EmailException.class, 
            () -> new Email(null)
        );   
        assertEquals("Email cannot be null or blank.", exception.getMessage());
    }

    @Test 
    void throwsWhenEmailIsBlank(){
        EmailException exception = assertThrows(
            EmailException.class, 
            () -> new Email("   ")
        );   
        assertEquals("Email cannot be null or blank.", exception.getMessage());
    }

    @ParameterizedTest 
    @ValueSource(strings = {
        "plainstring",              // Missing @
        "@domain.com",              // Missing local part
        "username@",                // Missing domain
        "user@name@domain.com",     // Multiple @ symbols
        "user@domain",              // Missing TLD extension
        "user@domain.",             // Trailing dot
        "user@.com",                // Missing domain name
        "user@domain.c",            // TLD too short
        "user@domain.123",          // Numeric TLD
        "user space@domain.com",    // Space in local part
        "user@domain space.com",    // Space in domain part
        "user..name@domain.com"     // Consecutive dots
    })
    void throwsWhenEmailHasInvalidFormat(String invalidEmail){
        EmailException exception = assertThrows(
            EmailException.class, 
            () -> new Email(invalidEmail)
        );
        assertEquals("Invalid email format.", exception.getMessage());
    }
}
