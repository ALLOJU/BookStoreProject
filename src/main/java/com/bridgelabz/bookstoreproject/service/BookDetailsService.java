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

    /**
     * addBook - used to add book details using save method provided in repository.
     * @param bookDto - bookdto for getting details of the book
     * @return
     */
    @Override
    public BookDetails addBook(BookStoredto bookDto) {
        BookDetails bookAdded = new BookDetails(bookDto);
        System.out.println(bookAdded);
        return bookRepo.save(bookAdded);
    }

    /**
     * showAllBooks - get all the details of the books using findAll() method
     * @return
     */
    @Override
    public List<BookDetails> showAllBooks() {
        List<BookDetails> allBooks = (List<BookDetails>) bookRepo.findAll();
        System.out.println("AllBook" + allBooks);
        return allBooks;
    }

    /**
     *  getBookById - get details of the given book id.here im using findByBookId method
     *  to find bookid and get the details of the books.if the book id is not found in the database,
     *  then it throws exception with book with id does not exist message.
     * @param bookId
     * @return
     */
    @Override
    public BookDetails getBookById(int bookId) {
        return bookRepo.findByBookId(bookId)
                .orElseThrow(() -> new UserRegistrationException("Book  with id " + bookId + " does not exist in database..!"));
    }
    /**
     *  deleteBook - delete details of the given book id.here im using findByBookId method
     *  to find bookid and delete the details of the books.if the book id is not found in the database,
     *  then it throws exception with book with id does not exist message.
     * @param bookId
     * @return
     */
    @Override
    public void deleteBook(int bookId) {
        BookDetails isBookPresent = this.getBookById(bookId);
        bookRepo.delete(isBookPresent);
    }
    /**
     *  getBookByName - get details of the given book name.here im using findByBookName method
     *  to find bookName and get the details of the books.if the book id is not found in the database,
     *  then it throws exception with book with id does not exist message.
     * @param bookName
     * @return
     */
    @Override
    public BookDetails getBookByName(String bookName) {
        return (BookDetails) bookRepo.findByBookName(bookName)
                .orElseThrow(() -> new UserRegistrationException("Book does not exist in database..!"));
    }

    /**
     * updateBook - update the book details for specific book id
     * @param bookId
     * @param bookDTO
     * @return
     */
    @Override
    public BookDetails updateBook(int bookId, BookStoredto bookDTO) {
        BookDetails bookData = this.getBookById(bookId);
        bookData.updateBook(bookDTO);
        return bookRepo.save(bookData);
    }

    /**
     *sortBookByAsc - sort the book details  in ascending using query in the repository
     * @return - returns list of books in sorting order
     */
    @Override
    public List<BookDetails> sortBookByAsc() {
        return bookRepo.sortBookAsc();
    }
    /**
     *sortBookByDesc - sort the book details  in descending order  using query in the repository
     * @return - returns list of books in sorting order
     */
    @Override
    public List<BookDetails> sortBookByDesc() {
        return bookRepo.sortBookDesc();
    }

    /**
     * updateBookQuantity - update the book quantity using book id.
     * here first we need to find the details of the given bookid and then set the quantity from the path provided
     * in the uri.then save the updated data in the repo.
     * @param bookId
     * @param bookQuantity
     * @return
     */
    @Override
    public BookDetails updateBookQuantity(int bookId, int bookQuantity) {
        BookDetails book = this.getBookById(bookId);
        book.setBookQuantity(bookQuantity);
        return bookRepo.save(book);
    }
    /**
     *  getBookByAuthorName - get details of the given book  Author name.here im using findByBookName method
     *  to find bookAuthor and get the details of the books.if the book id is not found in the database,
     *  then it throws exception with book with id does not exist message.
     * @param bookAuthor-given input name of the author
     * @return
     */
    @Override
    public BookDetails getBookByAuthorName(String bookAuthor) {
        return (BookDetails) bookRepo.findByBookAuthor(bookAuthor)
                .orElseThrow(() -> new UserRegistrationException("Book does not exist in database..!"));
    }




}
