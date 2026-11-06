package com.library.domain.utils;

import java.util.Objects;
import java.util.regex.Pattern;
import com.library.exception.utils.ISBNError;
import com.library.exception.utils.ISBNException;

public final class ISBN {  
    // Clean, robust pattern: checks that the unhyphenated string starts with 978/979 and contains exactly 13 digits
    private static final Pattern ISBN_13_CLEAN_PATTERN = Pattern.compile("^(?:978|979)\\d{10}$");

    private final String value;

    public ISBN(String isbn) {
        if(isbn == null) throw new ISBNException(ISBNError.NULL_ISBN);

        // 1. Remove hyphens and spaces first to equalize formatting variations
        String cleaned = isbn.trim().replace("-", "").replace(" ", "");

        // 2. Validate structure against the clean string
        if (!ISBN_13_CLEAN_PATTERN.matcher(cleaned).matches()) {
            throw new ISBNException(ISBNError.INVALID_FORMAT);
        }

        // 3. Run your excellent mathematical checksum
        if (!isValidCheckDigit(cleaned)) {
            throw new ISBNException(ISBNError.INVALID_CHECK_DIGIT);
        }

        this.value = cleaned;
    }

    private boolean isValidCheckDigit(String digits) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(digits.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int calculatedCheckDigit = (10 - (sum % 10)) % 10;
        int actualCheckDigit = Character.getNumericValue(digits.charAt(12));

        return calculatedCheckDigit == actualCheckDigit;
    }

    public String getValue() {
        return value;
    }

    @Override 
    public String toString() {
        // Safe, clean representation without guessing changing international hyphen lengths
        return value;
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ISBN other = (ISBN) obj;
        return Objects.equals(value, other.value);
    }

    @Override 
    public int hashCode() {
        return Objects.hash(value);
    }
}