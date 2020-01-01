package com.techyowls.repository;

import com.techyowls.entity.Author;
import com.techyowls.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}


