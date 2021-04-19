package ua.com.poseal.library.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.poseal.library.models.Book;
import ua.com.poseal.library.models.BookInUse;
import ua.com.poseal.library.models.Client;

import java.util.Optional;

public interface BookInUseRepository extends PagingAndSortingRepository<BookInUse, Long> {

    Optional<BookInUse> findByClientAndBook(Client client, Book book);
}
