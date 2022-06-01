package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.BookStoredto;
import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.exceptions.UserRegistrationException;
import com.bridgelabz.bookstoreproject.model.BookDetails;
import com.bridgelabz.bookstoreproject.repository.BookDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class BookDetailsService implements  IBookDetailsService{
    @Autowired
    private BookDetailsRepository bookRepo;
    @Override
    public BookDetails addBook(BookStoredto bookDto) {
        BookDetails bookAdded = new BookDetails(bookDto);
        System.out.println(bookAdded);
        return bookRepo.save(bookAdded);
    }

    @Override
    public List<BookDetails> showAllBooks() {
        List<BookDetails> allBooks = (List<BookDetails>) bookRepo.findAll();
        System.out.println("AllBook" + allBooks);
        return allBooks;
    }

    @Override
    public BookDetails getBookById(int bookId) {
        return bookRepo.findByBookId(bookId)
                .orElseThrow(() -> new UserRegistrationException("Book  with id " + bookId + " does not exist in database..!"));
    }

    @Override
    public void deleteBook(int bookId) {
        BookDetails isBookPresent = this.getBookById(bookId);
        bookRepo.delete(isBookPresent);
    }

    @Override
    public BookDetails getBookByName(String bookName) {
        return (BookDetails) bookRepo.findByBookName(bookName)
                .orElseThrow(() -> new UserRegistrationException("Book does not exist in database..!"));
    }

    @Override
    public BookDetails updateBook(int bookId, BookStoredto bookDTO) {
        BookDetails bookData = this.getBookById(bookId);
        bookData.updateBook(bookDTO);
        return bookRepo.save(bookData);
    }

    @Override
    public List<BookDetails> sortBookByAsc() {
        return bookRepo.sortBookAsc();
    }

    @Override
    public List<BookDetails> sortBookByDesc() {
        return bookRepo.sortBookDesc();
    }

    @Override
    public BookDetails updateBookQuantity(int bookId, int bookQuantity) {
        BookDetails book = this.getBookById(bookId);
        book.setBookQuantity(bookQuantity);
        return bookRepo.save(book);
    }


}
