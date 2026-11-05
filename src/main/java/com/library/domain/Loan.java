package com.library.domain;

import java.util.UUID;
import java.util.Objects;
import java.time.LocalDate;
import com.library.domain.utils.Formatter;
import com.library.exception.LoanError;
import com.library.exception.LoanException;
import com.library.exception.BookError;
import com.library.exception.BookException;
import com.library.exception.MemberError;
import com.library.exception.MemberException;
import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "loans")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Loan{
    @Id 
    private String loanID;

    @ManyToOne(fetch = FetchType.LAZY) // Many loans can feature the Same Book
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY) // Many loans can belong to the Same Member
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Book book, Member member, LocalDate dueDate){
        if(book == null) throw new BookException(BookError.NULL_BOOK);
        if(member == null) throw new MemberException(MemberError.NULL_MEMBER);
        if(dueDate == null) throw new LoanException(LoanError.NULL_DUE_DATE);

        this.loanID = UUID.randomUUID().toString();
        this.book = book;
        this.member = member;
        this.loanDate = LocalDate.now();

        if(dueDate.isBefore(this.loanDate)) throw new LoanException(LoanError.PAST_DUE_DATE);

        this.dueDate = dueDate;
        this.returnDate = null;
    }

    public String getID() {return loanID;}
    public Book getBook() {return book;}
    public Member getMember() {return member;}
    public LocalDate getLoanDate() {return loanDate;}
    public LocalDate getDueDate() {return dueDate;}
    public LocalDate getReturnDate() {return returnDate;}

    public boolean isReturned(){
        return returnDate != null;
    }

    public void changeDueDate(LocalDate newDueDate){
        if(isReturned()) throw new LoanException(LoanError.DUE_DATE_AFTER_RETURN);
        if(newDueDate == null) throw new LoanException(LoanError.NULL_DUE_DATE);
        if(newDueDate.isBefore(LocalDate.now())) throw new LoanException(LoanError.PAST_DUE_DATE);
        this.dueDate = newDueDate;
    }

    public void setReturnDate(){
        if(returnDate != null) throw new LoanException(LoanError.LOAN_ALREADY_RETURNED);
        this.returnDate = LocalDate.now();
    }

    @Override 
    public String toString(){
        if(returnDate == null){
            return String.format("""
                Loan ID: %s
                Book: %s
                Member: %s
                Loan date: %s
                Due date: %s
                """, 
                loanID, book.getTitle(), member.getName(), Formatter.DateToString(loanDate), Formatter.DateToString(dueDate));
        }
        return String.format("""
            Loan ID: %s
            Book: %s
            Member: %s
            Loan date: %s
            Due date: %s
            Return date: %s
            """, 
            loanID, book.getTitle(), member.getName(), Formatter.DateToString(loanDate), 
            Formatter.DateToString(dueDate), Formatter.DateToString(returnDate));
    }

    @Override 
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Loan other = (Loan) obj;
        return Objects.equals(loanID, other.loanID);
    }

    @Override 
    public int hashCode(){
        return Objects.hashCode(loanID);
    }
}