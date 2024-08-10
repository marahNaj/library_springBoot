package com.example.demo.service;

import com.example.demo.model.Patron;
import com.example.demo.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> findAll() {
        return patronRepository.findAll();
    }

    public Optional<Patron> findById(int id) {
        return patronRepository.findById(id);
    }

    public Patron save(Patron patron) {
        return patronRepository.save(patron);
    }

    public void deleteById(int id) {
        patronRepository.deleteById(id);
    }
}
