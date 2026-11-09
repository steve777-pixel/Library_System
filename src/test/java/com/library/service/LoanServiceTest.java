package com.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.Member;
import com.library.domain.utils.BookID;
import com.library.domain.utils.Email;
import com.library.domain.utils.ISBN;
import com.library.exception.BookException;
import com.library.exception.LoanException;
import com.library.exception.MemberException;

public class LoanServiceTest{
    LocalDate VALID_DUE_DATE = LocalDate.now().plusDays(14);

    Book book;
    Member member;
    Loan loan;
    LoanService loanService;

    @BeforeEach 
    void setup(){
        this.book = new Book(new BookID("AX6576456"), "Five Feet Apart", "Somebody", 
        new ISBN("978-0-306-40615-7"), 10);
        this.member = new Member("Steve", new Email("stevewatashi@gmail.com"));
        this.loanService = new LoanService();
    }

    // BORROW TESTS

    @Test 
    void borrowingSuccess(){
        assertEquals(0, member.getBorrowedBookCount());
        assertEquals(10, book.getAvailableCopies().intValue());
        assertFalse(member.isBorrowing(book));

        loanService.borrowBook(member, book, VALID_DUE_DATE);

        assertEquals(1, member.getBorrowedBookCount());        
        assertEquals(9, book.getAvailableCopies().intValue());
        assertTrue(member.isBorrowing(book));
    }

    @Test 
    void throwsWhenMemberIsNull(){
        MemberException exception = assertThrows(
            MemberException.class, 
            () -> loanService.borrowBook(null, book, VALID_DUE_DATE)
        );
        assertEquals("Member cannot be null.", exception.getMessage());
    }

    @Test 
    void throwsWhenBookIsNull(){
        BookException exception = assertThrows(
            BookException.class, 
            () -> loanService.borrowBook(member, null, VALID_DUE_DATE)
        );
        assertEquals("Book cannot be null.", exception.getMessage());
    }

    @Test 
    void throwsWhenDueDateIsNull(){
        LoanException exception = assertThrows(
            LoanException.class, 
            () -> loanService.borrowBook(member, book, null)
        );
        assertEquals("Due date cannot be null.", exception.getMessage());
    }

    @Test 
    void throwsWhenDueDateIsInThePast(){
        LoanException exception = assertThrows(
            LoanException.class, 
            () -> loanService.borrowBook(member, book, LocalDate.now().minusDays(10))
        );
        assertEquals("Due date cannot be in the past.", exception.getMessage());
    }

    @Test 
    void throwsWhenBookIsNotAvailable(){
        for(int i = 0; i < 10; i++) book.borrowCopy();
        assertFalse(book.isAvailable());

        BookException exception = assertThrows(
            BookException.class, 
            () -> loanService.borrowBook(member, book, VALID_DUE_DATE)
        );
        assertEquals("Book has no available copies.", exception.getMessage());
    }

    // RETURN TESTS

    private void performInitialBorrow(){
        loanService.borrowBook(member, book, VALID_DUE_DATE);
        List<Loan> activeLoans = member.getActiveLoans();
        this.loan = activeLoans.get(0);
    }

    @Test 
    void returnSuccess(){
        performInitialBorrow();
        
        assertEquals(null, loan.getReturnDate());
        assertEquals(9, book.getAvailableCopies());
        assertTrue(member.isBorrowing(book));

        loanService.returnBook(loan);

        assertEquals(LocalDate.now(), loan.getReturnDate());
        assertEquals(10, book.getAvailableCopies());
        assertFalse(member.isBorrowing(book));
    }

    @Test 
    void throwsWhenLoanIsNull(){
        LoanException exception = assertThrows(
            LoanException.class, 
            () -> loanService.returnBook(null)
        );
        assertEquals("Loan cannot be null.", exception.getMessage());
    }

    @Test 
    void throwsWhenLoanIsAlreadyReturned(){
        performInitialBorrow();
        loanService.returnBook(loan);
        
        LoanException exception = assertThrows(
            LoanException.class, 
            () -> loanService.returnBook(loan)
        );
        assertEquals("Loan can be returned only once.", exception.getMessage());
    }
}