package com.bridgelabz.bookstoreproject.repository;

import com.bridgelabz.bookstoreproject.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDetailsRepository extends CrudRepository<BookDetails,Integer> {
    Optional<BookDetails>  findByBookId(int bookId);

    Optional<Object> findByBookName(String bookName);
    @Query(value = "SELECT * FROM books order by book_price ASC", nativeQuery = true)
    List<BookDetails> sortBookAsc();
    @Query(value = "SELECT * FROM books order by book_price DESC ", nativeQuery = true)
    List<BookDetails> sortBookDesc();

    Optional<Object> findByBookAuthor(String bookAuthor);
}
