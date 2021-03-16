package ua.com.poseal.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.library.models.Book;
import ua.com.poseal.library.services.BookService;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.get(id);
    }

    @PostMapping("/book")
    public Book createBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping("/book/{id}")
    public Book editBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
