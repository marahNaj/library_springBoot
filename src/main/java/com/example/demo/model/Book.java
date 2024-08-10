package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.demo.model.BorrowingRecord;
import java.util.Set;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publication_year", nullable = false)
    private LocalDateTime publicationYear;

    @Column(name = "isbn", nullable = false, unique = true)
    private String ISBN;

    @Column(name = "meta_data", columnDefinition = "TEXT")
    private String metaData;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowingRecord> borrowingRecords;

    // Constructors
    public Book() {}

    public Book(String title, String author, LocalDateTime publicationYear, String ISBN, String metaData) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
        this.metaData = metaData;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(LocalDateTime publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
