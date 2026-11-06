package com.library.domain;

import com.library.domain.utils.BookID;
import com.library.domain.utils.ISBN;
import com.library.exception.BookException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Nested;

class BookTest{

    private static final BookID VALID_ID = new BookID("AX9091234");
    private static final ISBN VALID_ISBN = new ISBN("978-0-306-40615-7");

    @ParameterizedTest(name = "Should fail when: {5}")
    @MethodSource("provideInvalidBookAttributes")
    void throwsForInvalidBookAttributes(BookID id, String title, String author, ISBN isbn, Integer totalCopies, String scenarioName){
        assertThrows(
            IllegalArgumentException.class, 
            () -> new Book(id, title, author, isbn, totalCopies),
            () -> "Expected constructor failure for scenario: " + scenarioName
        );
    }   

    private static Stream<Arguments> provideInvalidBookAttributes(){
        return Stream.of(
            Arguments.of(VALID_ID, null, "Stephen King", VALID_ISBN, 10, "Null Title"),
            Arguments.of(VALID_ID, "The Shining", null, VALID_ISBN, 10, "Null Author Name"),
            Arguments.of(VALID_ID, "", "Stephen King", VALID_ISBN, 10, "Blank Title"),
            Arguments.of(VALID_ID, "The Shining", "", VALID_ISBN, 10, "Blank Author Name"),
            Arguments.of(VALID_ID, "The Shining", "Stephen King", VALID_ISBN, null, "Null Total Copies"),
            Arguments.of(VALID_ID, "The Shining", "Stephen King", VALID_ISBN, -2, "Negative Total Copies")
        );
    }

    @Nested
    class testsWithSetUp{
        private Book book;

        @BeforeEach 
        void setup(){
            BookID id = new BookID("AX9091234");
            ISBN isbn = new ISBN("978-0-306-40615-7");
            this.book = new Book(id, "The Shining", "Stephen King", isbn, 2);
        }

        @Test 
        void bookCreationSuccess(){
            assertEquals("AX9091234", book.getID().getValue());
            assertEquals("The Shining", book.getTitle());
            assertEquals("Stephen King", book.getAuthor());
            assertEquals("9780306406157", book.getISBN().getValue());
            assertEquals(2, book.getTotalCopies().intValue());
            assertEquals(2, book.getAvailableCopies().intValue());
        }

        @Test 
        void addRemoveCopiesSuccess(){
            assertTrue(book.isAvailable());
            book.removeCopies(2);
            assertEquals(0, book.getAvailableCopies());
            book.addCopies(7);
            assertEquals(7, book.getAvailableCopies());
            assertEquals(7, book.getTotalCopies());
        }

        @Test 
        void shouldThrowWhenCopiesAreNull(){
            BookException exception_1 = assertThrows(
                BookException.class, 
                () -> book.removeCopies(null)
            );
            assertEquals("The number of copies cannot be null.", exception_1.getMessage());

            BookException exception_2 = assertThrows(
                BookException.class, 
                () -> book.addCopies(null)
            );
            assertEquals("The number of copies cannot be null.", exception_2.getMessage());
        }

        @Test 
        void shouldThrowWhenCopiesAreNegative(){
            BookException exception_1 = assertThrows(
                BookException.class, 
                () -> book.removeCopies(-2)
            );
            assertEquals("The number of copies cannot be negative.", exception_1.getMessage());

            BookException exception_2 = assertThrows(
                BookException.class, 
                () -> book.addCopies(-2)
            );
            assertEquals("The number of copies cannot be negative.", exception_2.getMessage());
        }

        @Test 
        void shouldThrowWhenCopiesAreNotEnough(){
            BookException exception = assertThrows(
                BookException.class, 
                () -> book.removeCopies(10)
            );
            assertEquals("Not enough copies.", exception.getMessage());
        }

        @Test 
        void borrowCopySuccess(){
            book.borrowCopy();

            assertEquals(1, book.getAvailableCopies());
            assertTrue(book.isAvailable());

            book.borrowCopy();

            assertEquals(0, book.getAvailableCopies());
            assertFalse(book.isAvailable());
        }

        @Test 
        void throwsWhenBookIsNotAvailableToBorrow(){
            book.removeCopies(2);

            assertFalse(book.isAvailable());
            BookException exception = assertThrows(
                BookException.class, 
                () -> book.borrowCopy()
            );
            assertEquals("Book has no available copies.", exception.getMessage());
        }

        @Test 
        void returnCopySuccess(){
            book.borrowCopy();
            assertEquals(1, book.getAvailableCopies());

            book.returnCopy();
            assertEquals(2, book.getAvailableCopies());
        }

        @Test 
        void throwsWhenAvailableCopiesExceedTotalCopies(){
            assertEquals(book.getAvailableCopies(), book.getTotalCopies());
            BookException exception = assertThrows(
                BookException.class, 
                () -> book.returnCopy()
            );
            assertEquals("Available copies cannot exceed the number of total copies.", exception.getMessage());
        }

        @Test 
        void testOverrideEquals(){
            Book book_1 = new Book(book.getID(), "The Silent Patient", "Alexis", book.getISBN(), 10);
            assertFalse(book == book_1);
            assertTrue(book.equals(book_1));
        }
    }
}