package com.example.Proiect.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Book not found");
    }
}
