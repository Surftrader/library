package ua.com.poseal.library.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.poseal.library.models.BookHistory;

public interface BookHistoryRepository extends PagingAndSortingRepository<BookHistory, Long> {
}
