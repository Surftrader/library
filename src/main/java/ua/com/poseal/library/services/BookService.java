package ua.com.poseal.library.services;

import org.springframework.stereotype.Service;
import ua.com.poseal.library.models.Book;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    public List<Book> getAll() {
        Book book1 = new Book();
        book1.setName("Name");
        book1.setAuthor("Author");
        book1.setDescription("Description");
        book1.setPublisher("Publisher");
        book1.setIsbn("ISBN");
        book1.setYear(2021);

        Book book2 = new Book();
        book2.setName("Name");
        book2.setAuthor("Author");
        book2.setDescription("Description");
        book2.setPublisher("Publisher");
        book2.setIsbn("ISBN");
        book2.setYear(2021);

        return Arrays.asList(book1, book2);
    }

    public Book get(Long id) {
        Book book = new Book();
        book.setName("Name");
        book.setAuthor("Author");
        book.setDescription("Description");
        book.setPublisher("Publisher");
        book.setIsbn("ISBN");
        book.setYear(2021);

        return book;
    }

    public Book create(Book book) {
        return book;
    }

    public Book update(Long id, Book book) {
        return book;
    }

    public void delete(Long id) {

    }
}
