package com.example.demo.controller;

import com.example.demo.model.Patron;
import com.example.demo.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable int id) {
        return patronService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Patron createPatron(@RequestBody Patron patron) {
        return patronService.save(patron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable int id, @RequestBody Patron patronDetails) {
        return patronService.findById(id)
                .map(patron -> {
                    patron.setFirstName(patronDetails.getFirstName());
                    patron.setEmailAddress(patronDetails.getEmailAddress());
                    return ResponseEntity.ok(patronService.save(patron));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable int id) {
        return patronService.findById(id)
                .map(patron -> {
                    patronService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
