package ua.com.poseal.library.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.poseal.library.exeptions.NotFoundException;
import ua.com.poseal.library.models.Book;
import ua.com.poseal.library.models.BookHistory;
import ua.com.poseal.library.models.BookInUse;
import ua.com.poseal.library.models.Client;
import ua.com.poseal.library.repositories.BookHistoryRepository;
import ua.com.poseal.library.repositories.BookInUseRepository;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookUsageService {

    private final BookService bookService;
    private final ClientService clientService;
    private final BookHistoryRepository bookHistoryRepository;
    private final BookInUseRepository bookInUseRepository;

    public BookInUse takeBook(Long clientId, Long bookId) {
        Client client = clientService.get(clientId);
        Book book = bookService.get(bookId);

        book.setAvailable(false);

        BookInUse bookInUse = new BookInUse();
        bookInUse.setClient(client);
        bookInUse.setBook(book);

        log.info("Client {} took a book {}", client, bookId);

        return bookInUseRepository.save(bookInUse);
    }

    public BookHistory returnBook(Long clientId, Long bookId) {
        Client client = clientService.get(clientId);
        Book book = bookService.get(bookId);

        book.setAvailable(true);

        Optional<BookInUse> optional = bookInUseRepository.findByClientAndBook(client, book);

        if (!optional.isPresent()) {
            throw new NotFoundException(String.format("Client with id %d doesn't use a book with id %d", clientId, bookId));
        }

        BookHistory bookHistory = new BookHistory();
        bookHistory.setClient(client);
        bookHistory.setBook(book);
        bookHistory.setInUseFrom(optional.get().getInUseFrom());
        bookHistory.setInUseTo(LocalDate.now());

        bookInUseRepository.delete(optional.get());
        BookHistory save = bookHistoryRepository.save(bookHistory);

        log.info("Client {} returns a book{}", clientId, bookId);

        return save;
    }
}
