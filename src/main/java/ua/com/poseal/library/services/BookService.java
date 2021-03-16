package ua.com.poseal.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.poseal.library.models.Book;
import ua.com.poseal.library.repositories.BookRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        Iterator<Book> iterator = bookRepository.findAll().iterator();
        List<Book> books = new ArrayList<>();

        while (iterator.hasNext()) {
            books.add(iterator.next());
        }
        return books;
    }

    public Book get(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new RuntimeException("Book not found");
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        Book bookFromDB = get(id);
        bookFromDB.setName(book.getName());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setDescription(book.getDescription());
        bookFromDB.setPublisher(book.getPublisher());
        bookFromDB.setIsbn(book.getIsbn());
        bookFromDB.setYear(book.getYear());

        return bookRepository.save(bookFromDB);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
