package com.example.Proiect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.example.Proiect.exception.BookNotFoundException;
import com.example.Proiect.exception.BookIdMismatchException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ BookNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Book not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ BookIdMismatchException.class })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, String body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(body, headers, status);
    }
}
