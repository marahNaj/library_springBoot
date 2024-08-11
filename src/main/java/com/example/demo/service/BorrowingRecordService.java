package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.BorrowingRecord;
import com.example.demo.model.Patron;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingRecordRepository;
import com.example.demo.repository.PatronRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingRecordService {

     @Autowired
     private PatronRepository patronRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    public String assignBookToPatron(int patronId, int bookId , LocalDateTime dueAt) {
        Optional<Patron> optionalPatron = patronRepository.findById(patronId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalPatron.isPresent() && optionalBook.isPresent()) {
            Patron patron = optionalPatron.get();
            Book book = optionalBook.get();

            if (!book.isAvailable()) {
                BorrowingRecord borrowingRecord = new BorrowingRecord();
                borrowingRecord.setPatron(patron);
                borrowingRecord.setBook(book);
                borrowingRecord.setBorrowedAt(LocalDateTime.now());
                borrowingRecord.setDueAt(dueAt);
                borrowingRecordRepository.save(borrowingRecord);

                book.setAvailable(true);
                bookRepository.save(book);

                return "Book assigned successfully.";
            } else {
                return "Book is not available.";
            }
        } else {
            return "Patron or Book not found.";
        }
    }

     public String returnBook(int borrowingRecordId) {
        Optional<BorrowingRecord> optionalBorrowingRecord = borrowingRecordRepository.findById(borrowingRecordId);

        if (optionalBorrowingRecord.isPresent()) {
            BorrowingRecord borrowingRecord = optionalBorrowingRecord.get();
            borrowingRecord.setReturnedAt(LocalDateTime.now());
            borrowingRecordRepository.save(borrowingRecord);

            Book book = borrowingRecord.getBook();
            book.setAvailable(false);
            bookRepository.save(book);

            return "Book returned successfully.";
        } else {
            return "Borrowing record not found.";
        }
    }
}
