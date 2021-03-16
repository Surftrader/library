package ua.com.poseal.library.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.poseal.library.models.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    @Query("SELECT book FROM Book book WHERE LOWER(book.name) LIKE :query" +
            " OR LOWER(book.author) LIKE :query" +
            " OR LOWER(book.description) LIKE :query" +
            " OR LOWER(book.isbn) LIKE :query" +
            " OR LOWER(book.publisher) LIKE :query")
    Page<Book> findByQuery(String query, Pageable pageable);
}
