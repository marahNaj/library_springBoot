package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.BorrowingRecord;
import com.example.demo.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import com.example.demo.resources.BaseResponseApi;


import java.util.List;

@RestController
@RequestMapping("/api/Borrowing")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingService;

    @PostMapping("/assign")
    public ResponseEntity<BaseResponseApi<Void>> assignBook(@RequestParam int patronId, @RequestParam int bookId,@RequestParam LocalDateTime dueAt) {
        String responsString =  borrowingService.assignBookToPatron(patronId, bookId ,dueAt);
        BaseResponseApi<Void> response = new BaseResponseApi<>(true, responsString, null);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/return")
    public ResponseEntity<BaseResponseApi<Void>> returnBook(@RequestParam int borrowingRecordId) {
        String responsString = borrowingService.returnBook(borrowingRecordId);
        BaseResponseApi<Void> response = new BaseResponseApi<>(true, responsString, null);
        return ResponseEntity.ok(response);
    }
}
