package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.resources.BaseResponseApi;
import com.example.demo.service.BookService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<BaseResponseApi< List<Book> >> getAllBooks() {
        List<Book> books= bookService.findAll();
        BaseResponseApi< List<Book> > response = new BaseResponseApi<>(true, "Books retrieved successfully", books);
        return ResponseEntity.ok(response); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseApi<Book>> getBookById(@PathVariable int id) {

        Optional<Book> bookOptional = bookService.findById(id);

        if (bookOptional.isPresent()) {
            BaseResponseApi<Book> response = new BaseResponseApi<>(true, "Book retrieved successfully", bookOptional.get());
            return ResponseEntity.ok(response);
        } else {
            BaseResponseApi<Book> response = new BaseResponseApi<>(false, "Book not found", null);
            return ResponseEntity.status(404).body(response); // Return 404 Not Found
        }  
    }

    @PostMapping
    public ResponseEntity<BaseResponseApi<Book>> createBook(@RequestBody Book book) {
        Book Newbook = new Book();
        Newbook.setTitle(book.getTitle());
        Newbook.setAuthor(book.getAuthor());
        Newbook.setPublicationYear(book.getPublicationYear());
        Newbook.setISBN(book.getISBN());
        Newbook.setMetaData(book.getMetaData());
        Newbook.setMetaData(book.getMetaData());
        Newbook.setAvailable(false);
        bookService.save(Newbook);
        BaseResponseApi<Book> response = new BaseResponseApi<>(true, "Create Book successfully", Newbook);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseApi<Book>> updateBook(@PathVariable int id, @RequestBody Book bookDetails) {

        Optional<Book> bookOptional = bookService.findById(id);

        if (bookOptional.isPresent()) {
            Book updatedBook =  bookOptional.get();
            updatedBook.setTitle(bookDetails.getTitle());
            updatedBook.setAuthor(bookDetails.getAuthor());
            updatedBook.setPublicationYear(bookDetails.getPublicationYear());
            updatedBook.setISBN(bookDetails.getISBN());
            updatedBook.setMetaData(bookDetails.getMetaData());
            updatedBook.setUpdatedAt();
            bookService.save(updatedBook);
            BaseResponseApi<Book> response = new BaseResponseApi<>(true, "Book updated successfully", updatedBook);
            return ResponseEntity.ok(response);
        } else {
            BaseResponseApi<Book> response = new BaseResponseApi<>(false, "Book not found", null);
            return ResponseEntity.status(404).body(response); // Return 404 Not Found
        }  
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseApi<Void>> deleteBook(@PathVariable int id) {
        Optional<Book> bookOptional = bookService.findById(id);

        if (bookOptional.isPresent()) { 
            if(!bookOptional.get().getBorrowingRecords().isEmpty()) {
                BaseResponseApi<Void> response = new BaseResponseApi<>(false, "You Cannot delete Book, it's Borrowing for patrons ", null);
                return ResponseEntity.ok(response);
            } else {
                bookService.deleteById(id);
                BaseResponseApi<Void> response = new BaseResponseApi<>(false, "Delete Book successfully", null);
                return ResponseEntity.ok(response);
    
            }
        }
        else {
            BaseResponseApi<Void> response = new BaseResponseApi<>(false, "Book not found", null);
            return ResponseEntity.status(404).body(response); // Return 404 Not Found
        }  
        
    }
}
