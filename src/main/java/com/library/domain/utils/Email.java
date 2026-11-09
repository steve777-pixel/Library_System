package com.library.domain.utils;

import java.util.regex.Pattern;

import com.library.exception.utils.EmailError;
import com.library.exception.utils.EmailException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public record Email(@Column(name = "email") String value) implements Serializable{

    // A much safer, standard regex that ensures an @ and a valid domain extension (.com, .org, etc.)
    // This enforces that dots must be separated by letters or numbers
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");


    public Email {
        if (value == null || value.isBlank()) {
            throw new EmailException(EmailError.NULL_EMAIL);
        }
        
        // Clean the input to prevent accidental spacing issues
        value = value.trim();

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new EmailException(EmailError.INVALID_EMAIL_FORMAT);
        }
    }
}
