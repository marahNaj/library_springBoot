CREATE DATABASE IF NOT EXISTS springbootdb;

USE springbootdb;

CREATE TABLE IF NOT EXISTS patrons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS springbootdb.books (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    avaliable boolean NOT NULL DEFAULT false
    isbn VARCHAR(255) NOT NULL,
    meta_data JSON NULL DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS springbootdb.borrowing_record (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    patron_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (patron_id) REFERENCES patrons(id)
);

INSERT INTO springbootdb.patrons (first_name, last_name, email_address) 
VALUES ('John', 'Doe', 'john.doe@example.com') , ('Jane', 'Smith', 'jane.smith@example.com');

INSERT INTO springbootdb.books (title, author, publication_year , ISBN , meta_data) 
VALUES ('book 1', 'auther 1', '2024-08-09 23:54:23' , '1234' , '[]') , ('book 2', 'auther 2', '2024-08-08 23:54:23' , '4321' , '[]');




CREATE TABLE springbootdb.borrowing_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    patron_id BIGINT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);