package com.library.domain;

import com.library.domain.utils.ISBN;
import com.library.domain.utils.BookID;
import com.library.domain.utils.Validation;
import com.library.domain.utils.BookIDConverter;
import com.library.domain.utils.ISBNConverter;
import com.library.exception.BookException;
import com.library.exception.BookError;
import java.util.Objects;
import jakarta.persistence.*;
import lombok.*;

// @NonNull 
@Entity
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @AllArgsConstructor
public class Book {
    @Id // Remove @GeneratedValue if your ID is custom string-based (e.g., AB1234567)
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Convert(converter = BookIDConverter.class)
    private BookID id;
    private String title;
    private String author;

    @Convert(converter = ISBNConverter.class)
    private ISBN isbn;

    private Integer totalCopies;
    private Integer availableCopies;

    public Book(BookID id, String title, String author, ISBN isbn, Integer totalCopies){
        this.id = id;
        this.title = Validation.validateNotBlank(title, "Book title");
        this.author = Validation.validateNotBlank(author, "Author name");
        this.isbn = isbn;
        this.totalCopies = Validation.validateNotNegative(totalCopies, "The number of total copies");
        this.availableCopies = totalCopies; // All books are available when created/added for the first time
    }

    public BookID getID() {return id;}
    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public ISBN getISBN() {return isbn;}
    public Integer getTotalCopies() {return totalCopies;}
    public Integer getAvailableCopies() {return availableCopies;}

    public boolean isAvailable(){
        return availableCopies > 0;
    }

    public void addCopies(Integer copies){
        if(copies <= 0) throw new BookException(BookError.NEGATIVE_COPIES);
        this.totalCopies += copies;
        this.availableCopies += copies;
    }

    public void removeCopies(Integer copies){
        if(copies <= 0) throw new BookException(BookError.NEGATIVE_COPIES);
        if(copies > availableCopies) throw new BookException(BookError.COPIES_OUT_OF_RANGE);
        this.availableCopies -= copies;
    }

    public void borrowCopy(){
        if(!isAvailable()) throw new BookException(BookError.BOOK_NOT_AVAILABLE);
        this.availableCopies--;
    }

    public void returnCopy(){
        if(availableCopies >= totalCopies) throw new BookException(BookError.TOTAL_COPIES_EXCEEDED);
        this.availableCopies++;
    }

    @Override 
    public String toString(){
        return String.format("""
            Book ID: %s
            Title: %s
            Author: %s
            ISBN: %s
            Total copies: %d
            Available copies: %d
            """, id, title, author, isbn, totalCopies, availableCopies);
    }

    @Override 
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Book other = (Book) obj;
        return Objects.equals(id, other.id);
    }

    @Override 
    public int hashCode(){
        return Objects.hashCode(id);
    }
}
