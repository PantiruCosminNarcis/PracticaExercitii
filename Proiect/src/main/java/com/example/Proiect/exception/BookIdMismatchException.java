package com.example.Proiect.exception;

public class BookIdMismatchException extends RuntimeException {
    public BookIdMismatchException() {
        super("Book ID mismatch");
    }
}

