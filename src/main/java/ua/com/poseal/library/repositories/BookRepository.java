package ua.com.poseal.library.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.com.poseal.library.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
