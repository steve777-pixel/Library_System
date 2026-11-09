package com.library.repository;

import com.library.domain.Book;
import com.library.domain.utils.BookID;
import com.library.domain.utils.ISBN;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, BookID> {

    Optional<Book> findByIsbn(ISBN isbn);

    boolean existsByIsbn(ISBN isbn);

    List<Book> findByAuthorIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    List<Book> findByAvailableCopiesGreaterThan(Integer count);

    long deleteByIsbn(ISBN isbn);
}