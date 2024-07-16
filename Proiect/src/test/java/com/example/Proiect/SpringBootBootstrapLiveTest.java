package com.example.Proiect;

import com.example.Proiect.model.Book;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT = "http://localhost:8081/api/books";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = API_ROOT;
    }

    private Book createRandomBook() {
        Book book = new Book();
        book.setTitle(randomAlphabetic(10));
        book.setAuthor(randomAlphabetic(15));
        return book;
    }

    private String createBookAsUri(Book book) {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(book)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void whenGetAllBooks_thenOK() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenGetBooksByTitle_thenOK() {
        Book book = createRandomBook();
        createBookAsUri(book);
        Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());

        assertEquals(200, response.getStatusCode());
        assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    public void whenGetCreatedBookById_thenOK() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);
        Response response = RestAssured.get(location);

        assertEquals(200, response.getStatusCode());
        assertEquals(book.getTitle(), response.jsonPath().get("title"));
    }

    @Test
    public void whenGetNotExistBookById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + 9999);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void whenCreateNewBook_thenCreated() {
        Book book = createRandomBook();
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(book)
                .post(API_ROOT);

        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void whenInvalidBook_thenError() {
        Book book = createRandomBook();
        book.setAuthor(null);
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(book)
                .post(API_ROOT);

        assertEquals(400, response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedBook_thenUpdated() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);
        book.setId(Long.parseLong(location.split("/")[5]));
        book.setAuthor("newAuthor");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(book)
                .put(location);

        assertEquals(200, response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals("newAuthor", response.jsonPath().get("author"));
    }

    @Test
    public void whenDeleteCreatedBook_thenOk() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);
        Response response = RestAssured.delete(location);

        assertEquals(200, response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(404, response.getStatusCode());
    }
}
