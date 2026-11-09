package com.library.domain;

import com.library.domain.utils.MemberStatus;
import com.library.domain.utils.Validation;
import com.library.domain.utils.Email;
import com.library.exception.LoanError;
import com.library.exception.LoanException;
import com.library.exception.MemberError;
import com.library.exception.MemberException;
import com.library.domain.utils.Formatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{
    private String name;

    @EmbeddedId 
    private Email email;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDate joinedDate;
    private LocalDate membershipExpiryDate;
    private Integer suspensionPoints; // one late return = 1 suspension point

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    private static final Integer MAXIMUM_ALLOWED_SUSPENSION_POINTS = 10;
    private static final Integer MEMBERSHIP_PERIOD = 2;
    private static final Integer BORROWING_LIMIT = 5;

    public Member(String name, Email email){
        this.name = Validation.validateNotBlank(name, "Member name");
        this.email = email;
        this.status = MemberStatus.ACTIVE; // All memberships are active when created
        this.joinedDate = LocalDate.now();
        this.membershipExpiryDate = this.joinedDate.plusYears(MEMBERSHIP_PERIOD);
        this.suspensionPoints = 0; // All members start with 0 suspension points
    }

    public String getName() {return name;}
    public Email getEmail() {return email;}
    public MemberStatus getMemberStatus() {return status;}
    public LocalDate getJoinedDate() {return joinedDate;}
    public LocalDate getMembershipExpiryDate() {return membershipExpiryDate;}
    public int getSuspensionPoints() {return suspensionPoints;}

    public List<Loan> getActiveLoans(){
        return loans.stream().filter(loan -> !loan.isReturned()).toList();
    }

    public int getBorrowedBookCount(){
        return getActiveLoans().size();
    }

    public void updateMembershipStatus(){
        if(membershipExpiryDate.isBefore(LocalDate.now())){
            this.status = MemberStatus.EXPIRED;
        }
    }

    public boolean isSuspended(){
        return suspensionPoints > MAXIMUM_ALLOWED_SUSPENSION_POINTS;
    }

    public void addSuspensionPoint(){
        this.suspensionPoints++;
        if(isSuspended()) this.status = MemberStatus.SUSPENDED;
    }

    public void resetSuspension(){
        this.suspensionPoints = 0;
        if(this.membershipExpiryDate.isAfter(LocalDate.now())) this.status = MemberStatus.ACTIVE;
    }

    public boolean isActive(){
        updateMembershipStatus();
        return status == MemberStatus.ACTIVE;
    }

    public void renewMembership(){
        if(isSuspended()) throw new MemberException(MemberError.MEMBER_SUSPENDED, "Cannot renew membership when suspended.");
        this.membershipExpiryDate = LocalDate.now().plusYears(MEMBERSHIP_PERIOD);
        this.status = MemberStatus.ACTIVE;
    }

    public boolean isBorrowing(Loan loan){
        return getActiveLoans().stream().anyMatch(existing -> existing.getBook().equals(loan.getBook()));
    }

    public boolean isBorrowing(Book book){
        return getActiveLoans().stream().anyMatch(existing -> existing.getBook().getID().equals(book.getID()));
    }

    public void registerLoan(Loan loan){
        if(loan == null) throw new LoanException(LoanError.NULL_LOAN);

        if(!loan.getMember().equals(this)) throw new MemberException(MemberError.INVALID_LOAN);

        if(!isActive()) throw new MemberException(MemberError.MEMBER_NOT_ACTIVE, "Cannot take loans when inactive.");

        if(getBorrowedBookCount() >= BORROWING_LIMIT) throw new MemberException(MemberError.BORROWING_LIMIT_REACHED);

        if(isBorrowing(loan)) throw new MemberException(MemberError.BORROWING_DUPLICATE);

        loans.add(loan);
    }    

    @Override 
    public String toString(){
        return String.format("""
            Name: %s
            Email: %s
            Status: %s
            Joined date: %s
            Membership expiry date: %s
            Currently borrowing: %d
        """, name, email, status.getDescription(), Formatter.DateToString(joinedDate), 
        Formatter.DateToString(membershipExpiryDate), getBorrowedBookCount());
    }

    @Override 
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Member other = (Member) obj;
        return Objects.equals(email, other.email);
    }

    @Override 
    public int hashCode(){
        return Objects.hashCode(email);
    }
}