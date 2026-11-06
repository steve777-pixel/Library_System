package com.library.service;

import com.library.domain.Member;
import com.library.exception.BookError;
import com.library.exception.BookException;
import com.library.exception.LoanError;
import com.library.exception.LoanException;
import com.library.exception.MemberError;
import com.library.exception.MemberException;
import com.library.domain.Book;
import com.library.domain.Loan;
import java.time.LocalDate;

public class LoanService{
    public void borrowBook(Member member, Book book, LocalDate dueDate){
        if(member == null) throw new MemberException(MemberError.NULL_MEMBER);
        if(book == null) throw new BookException(BookError.NULL_BOOK);

        Loan loan = new Loan(book, member, dueDate);

        if(!book.isAvailable()) throw new BookException(BookError.BOOK_NOT_AVAILABLE);

        member.registerLoan(loan);
        book.borrowCopy();
    }

    public void returnBook(Loan loan){
        if(loan == null) throw new LoanException(LoanError.NULL_LOAN);

        if(loan.isReturned()) throw new LoanException(LoanError.LOAN_ALREADY_RETURNED);

        Book book = loan.getBook();
        Member member = loan.getMember();

        loan.setReturnDate();
        book.returnCopy();
        
        if(loan.getDueDate().isBefore(LocalDate.now())) member.addSuspensionPoint();
    }
}