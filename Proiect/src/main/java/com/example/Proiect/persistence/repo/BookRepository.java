package com.example.Proiect.persistence.repo;
import com.example.Proiect.model.Book;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
