package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Patron;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getPatronById(@PathVariable int id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updatePatron(@PathVariable int id, @RequestBody Book bookDetails) {
        return bookService.findById(id)
                .map(book -> {
                    // book.setFirstName(bookDetails.getFirstName());
                    // book.setEmailAddress(bookDetails.getEmailAddress());
                    return ResponseEntity.ok(bookService.save(book));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable int id) {
        return bookService.findById(id)
                .map(patron -> {
                    bookService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
