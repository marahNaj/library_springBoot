package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Patron;
import com.example.demo.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.resources.BaseResponseApi;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public ResponseEntity<BaseResponseApi< List<Patron> >> getAllPatrons() {
        List<Patron> patrons= patronService.findAll();
        BaseResponseApi< List<Patron> > response = new BaseResponseApi<>(true, "Patrons retrieved successfully", patrons);
        return ResponseEntity.ok(response); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseApi<Patron>> getPatronById(@PathVariable int id) {
         Optional<Patron> patronOptional = patronService.findById(id);

        if (patronOptional.isPresent()) {
            BaseResponseApi<Patron> response = new BaseResponseApi<>(true, "Patron retrieved successfully", patronOptional.get());
            return ResponseEntity.ok(response);
        } else {
            BaseResponseApi<Patron> response = new BaseResponseApi<>(false, "Patron not found", null);
            return ResponseEntity.status(404).body(response); // Return 404 Not Found
        }  
    }

    @PostMapping
    public ResponseEntity<BaseResponseApi<Patron>>  createPatron(@RequestBody Patron patron) {

        Patron NewPatron = patronService.save(patron);
        BaseResponseApi<Patron> response = new BaseResponseApi<>(true, "Create Book successfully", NewPatron);
        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseApi<Patron>> updatePatron(@PathVariable int id, @RequestBody Patron patronDetails) {


        Optional<Patron> patronOptional = patronService.findById(id);

        if (patronOptional.isPresent()) {
            Patron updatedPatron =  patronOptional.get();
            updatedPatron.setFirstName(patronDetails.getFirstName());
            updatedPatron.setLastName(patronDetails.getLastName());
            updatedPatron.setEmailAddress(patronDetails.getEmailAddress());
            updatedPatron.setUpdatedAt();
            patronService.save(updatedPatron);
            BaseResponseApi<Patron> response = new BaseResponseApi<>(true, "Patron Updated successfully", updatedPatron);
            return ResponseEntity.ok(response);
        } else {
            BaseResponseApi<Patron> response = new BaseResponseApi<>(false, "Patron not found", null);
            return ResponseEntity.status(404).body(response); // Return 404 Not Found
        }  
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<BaseResponseApi<Void>>  deletePatron(@PathVariable int id) {
        Optional<Patron> patronOptional = patronService.findById(id);

        if (patronOptional.isPresent()) { 

            if(!patronOptional.get().getBorrowingRecords().isEmpty()) {
                BaseResponseApi<Void> response = new BaseResponseApi<>(false, "You Cannot delete Patron, it has books borrowing ", null);
                return ResponseEntity.ok(response);
            } else {
                patronService.deleteById(id);
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
