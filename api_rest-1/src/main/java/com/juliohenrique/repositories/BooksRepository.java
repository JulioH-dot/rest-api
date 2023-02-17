package com.juliohenrique.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juliohenrique.model.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long>{

}
