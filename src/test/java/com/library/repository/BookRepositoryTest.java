package com.library.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.library.domain.Book;
import com.library.domain.utils.BookID;
import com.library.domain.utils.ISBN;

@DataJpaTest 
class BookRepositoryTest{

    @Autowired 
    public BookRepository bookRepository;

    @Autowired 
    private TestEntityManager entityManager;

    @BeforeEach 
    void setup(){
        BookID sampleBookID = new BookID("AD8990123");
        ISBN sampleIsbn = new ISBN("978-0132350884");
        Book sampleBook = new Book(sampleBookID, "The Shining", "Stephen King", sampleIsbn, 10);
        entityManager.persistAndFlush(sampleBook);
        entityManager.clear();
    }

    @Test 
    void findById_WhenBookExists_ReturnsBook(){
        Optional<Book> foundBook = bookRepository.findById(new BookID("AD8990123"));
        assertTrue(foundBook.isPresent());
    }

    @Test 
    void findByIsbn_WhenBookExists_ReturnBook(){
        Optional<Book> foundBook = bookRepository.findByIsbn(new ISBN("978-0132350884"));
        assertTrue(foundBook.isPresent());
    }

    @Test 
    void findById_WhenBookDoesNotExist_ReturnsEmpty(){
        BookID nonExistentId = new BookID("ZZ9999999");

        Optional<Book> foundBook = bookRepository.findById(nonExistentId);

        assertTrue(foundBook.isEmpty());
    }

    @Test 
    void findByIsbn_WhenBookDoesNotExist_ReturnsEmpty(){
        ISBN nonExistentIsbn = new ISBN("978-0201616224");

        Optional<Book> foundBook = bookRepository.findByIsbn(nonExistentIsbn);

        assertTrue(foundBook.isEmpty());
    }
}