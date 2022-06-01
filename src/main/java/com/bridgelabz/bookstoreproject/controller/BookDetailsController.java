package com.bridgelabz.bookstoreproject.controller;

import com.bridgelabz.bookstoreproject.dto.BookStoredto;
import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.model.BookDetails;
import com.bridgelabz.bookstoreproject.service.IBookDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookDetailsController {
    @Autowired
    public IBookDetailsService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<ResponseDto> addBook(@Valid @RequestBody BookStoredto bookDto) {
        BookDetails addBook = bookService.addBook(bookDto);
        log.debug("Data"+addBook);
        ResponseDto dto = new ResponseDto("Book Added Successfully",addBook,null);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/getbooks")
    public ResponseEntity<ResponseDto> getAllBooks() {
        List<BookDetails> allBooks = bookService.showAllBooks();
        ResponseDto dto = new ResponseDto("All Books Retrieved successfully:", allBooks,null);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/getBook/{bookId}")
    public ResponseEntity<ResponseDto> getOneBook(@PathVariable int bookId)
    {
        BookDetails getOneBook = bookService.getBookById(bookId);
        ResponseDto dto = new ResponseDto("Book retrieved successfully   "+bookId, getOneBook,null);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable("bookId") int bookId) {
        bookService.deleteBook(bookId);
        ResponseDto response = new ResponseDto("Delete call success for id ", "deleted id:" + bookId,null);
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

    }
    @GetMapping("/getBookByName/{bookName}")
    public ResponseEntity<ResponseDto> getOneBookByName(@PathVariable String bookName)
    {
        BookDetails getOneBook = bookService.getBookByName(bookName);
        ResponseDto dto = new ResponseDto("Book retrieved successfully"+bookName, getOneBook,null);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseDto> updateBookData(@PathVariable("bookId") int bookId,
                                                      @Valid @RequestBody BookStoredto bookDTO) {
        BookDetails updateBook = bookService.updateBook(bookId, bookDTO);
        log.debug(" After Update " + updateBook.toString());
        ResponseDto response = new ResponseDto("Updated  for" + bookId, updateBook,null);
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

    }
    @GetMapping("/getBooksByPriceAsc")
    public ResponseEntity<ResponseDto> sortBookByPriceAsc() {
        List<BookDetails> sortBookByPriceAsc = bookService.sortBookByAsc();
        if (!sortBookByPriceAsc.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto("Books Found",sortBookByPriceAsc,null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("No Books Found", HttpStatus.NOT_FOUND.value(),null));
    }

    @GetMapping("/getBooksByPriceDesc")
    public ResponseEntity<ResponseDto> sortBookByPriceDesc() {
        List<BookDetails> sortBookByPriceDesc = bookService.sortBookByDesc();
        if (!sortBookByPriceDesc.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto("Books Found",sortBookByPriceDesc,null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("No Books Found", HttpStatus.NOT_FOUND.value(),null));
    }
    @PutMapping("/updatequantity/{bookId}/{bookQuantity}")
    public ResponseEntity<ResponseDto> updateBookQuantity(@PathVariable int bookId, @PathVariable int bookQuantity) {
        BookDetails updateBookQuantity = bookService.updateBookQuantity(bookId, bookQuantity);
        ResponseDto response = new ResponseDto("Book Quantity Update is success for id " + bookId, updateBookQuantity,null);
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
    }




}
