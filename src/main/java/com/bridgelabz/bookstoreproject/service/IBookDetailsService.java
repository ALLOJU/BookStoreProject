package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.BookStoredto;
import com.bridgelabz.bookstoreproject.model.BookDetails;

import java.util.List;

public interface IBookDetailsService {

    BookDetails addBook(BookStoredto bookDto);

    List<BookDetails> showAllBooks();

    BookDetails getBookById(int bookId);

    void deleteBook(int bookId);

    BookDetails getBookByName(String bookName);

    BookDetails updateBook(int bookId, BookStoredto bookDTO);

    List<BookDetails> sortBookByAsc();

    List<BookDetails> sortBookByDesc();

    BookDetails updateBookQuantity(int bookId, int bookQuantity);

    BookDetails getBookByAuthorName(String bookAuthor);
}
