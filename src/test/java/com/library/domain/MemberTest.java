package com.library.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.library.domain.utils.BookID;
import com.library.domain.utils.Email;
import com.library.domain.utils.ISBN;
import com.library.exception.LoanException;
import com.library.exception.MemberException;

public class MemberTest{

    Member member;
    Book book;
    Loan loan;

    @Test 
    void throwsWhenNameIsNull(){
        Email email = new Email("stevewatashi@gmail.com");
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> new Member(null, email)
        );
        assertEquals("Member name cannot be null.", exception.getMessage());
    }

    @Test 
    void throwsWhenNameIsBlank(){
        Email email = new Email("stevewatashi@gmail.com");
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> new Member("", email)
        );
        assertEquals("Member name cannot be empty.", exception.getMessage());
    }

    @BeforeEach 
    void setup(){
        Email email = new Email("stevewatashi@gmail.com");
        this.member = new Member("Steve", email);
        this.book = new Book(new BookID("AX6576456"), "Five feet Apart", "Somebody", new ISBN("978-0-306-40615-7"), 10);
        this.loan = new Loan(book, member, LocalDate.now().plusDays(14));
    }

    @Test 
    void memberCreationSuccess(){
        assertEquals("Steve", member.getName());
        assertEquals("stevewatashi@gmail.com", member.getEmail().value());
        assertEquals("Active", member.getMemberStatus().getDescription());
        assertEquals(LocalDate.now(), member.getJoinedDate());
        assertEquals(LocalDate.now().plusYears(2), member.getMembershipExpiryDate());
        assertEquals(0, member.getSuspensionPoints());
        assertEquals(0, member.getBorrowedBookCount());
    }

    @Test 
    void testSuspension(){
        assertFalse(member.isSuspended());

        member.addSuspensionPoint();
        assertEquals(1, member.getSuspensionPoints());
        assertEquals("Active", member.getMemberStatus().getDescription());

        for(int i = 0; i < 10; i++) member.addSuspensionPoint();

        assertEquals(11, member.getSuspensionPoints());
        assertEquals("Suspended", member.getMemberStatus().getDescription());

        assertTrue(member.isSuspended());
        assertFalse(member.isActive());

        member.resetSuspension();

        assertEquals(0, member.getSuspensionPoints());
        assertTrue(member.isActive());
        assertFalse(member.isSuspended());
    }

    @Test 
    void renewMembershipSuccess(){
        member.renewMembership();

        assertEquals(LocalDate.now().plusYears(2), member.getMembershipExpiryDate());
    }

    @Test 
    void cannotRenewMembershipWhenSuspended(){
        for(int i = 0; i < 11; i++) member.addSuspensionPoint();

        MemberException exception = assertThrows(
            MemberException.class, 
            () -> member.renewMembership()
        );
        assertEquals("Cannot renew membership when suspended.", exception.getMessage());
    }

    @Test 
    void testOverrideEquals(){
        Member member_1 = new Member("Alexa", new Email("stevewatashi@gmail.com"));
        assertFalse(member == member_1);
        assertTrue(member.equals(member_1));
    }
    
    @Test 
    void registerLoanSuccess(){
        member.registerLoan(loan);
        
        assertTrue(member.isBorrowing(loan));
    }
    
    @Test 
    void throwsWhenLoanIsNull(){
        LoanException exception = assertThrows(
            LoanException.class, 
            () -> member.registerLoan(null)
        );
        assertEquals("Loan cannot be null.", exception.getMessage());
    }

    @Test 
    void throwsWhenMemberDoesNotOwnTheLoan(){
        Member other_member = new Member("Alexa", new Email("hshakhdkauesda@gmail.com"));
        MemberException exception = assertThrows(
            MemberException.class, 
            () -> other_member.registerLoan(loan)
        );
        assertEquals("Member does not own this loan.", exception.getMessage());
    }

    @Test 
    void throwsWhenMemberIsNotActive(){
        for(int i = 0; i < 11; i++) member.addSuspensionPoint();
        MemberException exception = assertThrows(
            MemberException.class, 
            () -> member.registerLoan(loan)
        );
        assertEquals("Cannot take loans when inactive.", exception.getMessage());
    }

    @Test 
    void throwsWhenMemberAlreadyOwnsTheLoan(){
        member.registerLoan(loan);
        MemberException exception = assertThrows(
            MemberException.class, 
            () -> member.registerLoan(loan)
        );
        assertEquals("Member already borrowed this book.", exception.getMessage());
    }
}