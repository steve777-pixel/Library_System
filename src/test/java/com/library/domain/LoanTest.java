package com.library.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.library.domain.utils.BookID;
import com.library.domain.utils.Email;
import com.library.domain.utils.ISBN;
import com.library.exception.BookException;
import com.library.exception.LoanException;
import com.library.exception.MemberException;

class LoanTest{

    Book book;
    Member member;
    Loan loan;

    @BeforeEach 
    void setup(){
        this.book = new Book(new BookID("AX6576456"), "Five Feet Apart", "Somebody", new ISBN("978-0-306-40615-7"), 10);
        this.member = new Member("Steve", new Email("stevewatashi@gmail.com"));
        this.loan = new Loan(book, member, LocalDate.now().plusDays(14));    
    }

    @Test 
    void loanCreationSuccess(){
        System.out.println(loan.getID());
        assertEquals("Five Feet Apart", loan.getBook().getTitle());
        assertEquals("Steve", loan.getMember().getName());
        assertEquals(LocalDate.now().plusDays(14), loan.getDueDate());
        assertEquals(null, loan.getReturnDate());
    }

    @Test 
    void throwsWhenAnyAttributeIsNull(){
        BookException book_exception = assertThrows(
            BookException.class, 
            () -> new Loan(null, member, LocalDate.now().plusDays(14))
        );
        assertEquals("Book cannot be null.", book_exception.getMessage());

        MemberException member_exception = assertThrows(
            MemberException.class, 
            () -> new Loan(book, null, LocalDate.now().plusDays(14))
        );
        assertEquals("Member cannot be null.", member_exception.getMessage());

        LoanException loan_exception = assertThrows(
            LoanException.class, 
            () -> new Loan(book, member, null)
        );
        assertEquals("Due date cannot be null.", loan_exception.getMessage());
    }

    @Test 
    void throwsWhenDueDateIsInThePast(){
        LoanException exception = assertThrows(
            LoanException.class, 
            () -> new Loan(book, member, LocalDate.now().minusDays(10))
        );
        assertEquals("Due date cannot be in the past.", exception.getMessage());
    }

    @Test 
    void changeDueDateSuccess(){
        assertEquals(LocalDate.now().plusDays(14),loan.getDueDate());

        loan.changeDueDate(loan.getDueDate().plusDays(10));

        assertEquals(LocalDate.now().plusDays(24),loan.getDueDate());
    }

    @Test 
    void throwsWhenLoanIsReturned(){
        loan.setReturnDate();

        LoanException exception = assertThrows(
            LoanException.class, 
            () -> loan.changeDueDate(LocalDate.now().plusDays(15))
        );
        assertEquals("Due date cannot be modified after returning.", exception.getMessage());
    }

    @ParameterizedTest(name = "Should fail when: {1}")
    @MethodSource("provideInvalidDueDateAttributes")
    void throwsForInvalidDueDateAttributes(LocalDate newDate, String scenarioName){
        assertThrows(
            LoanException.class, 
            () -> loan.changeDueDate(newDate),
            () -> "Expected constructor failure for scenario: " + scenarioName
        );
    }   
    
    private static Stream<Arguments> provideInvalidDueDateAttributes(){
        return Stream.of(
            Arguments.of(null, "Null Due Date"),
            Arguments.of(LocalDate.now().minusDays(10), "Past Due Date")
        );
    }

    @Test 
    void throwsWhenReturningMultipleTimes(){
        loan.setReturnDate();

        LoanException exception = assertThrows(
            LoanException.class, 
            () -> loan.setReturnDate()
        );
        assertEquals("Loan can be returned only once.", exception.getMessage());
    }

    @Test 
    void testOverrideEquals(){
        Loan newLoan = new Loan(book, member, LocalDate.now().plusDays(14));
        assertFalse(newLoan.equals(loan));
        assertFalse(newLoan == loan);
    }
}