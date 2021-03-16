package ua.com.poseal.library.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @GetMapping("/book")
    public String getAllBooks() {
        return "All the books";
    }

    @GetMapping("/book/{id}")
    public String getBookById(@PathVariable Long id) {
        return "Book:" + id;
    }

    @PostMapping("/book")
    public String createBook() {
        return "Created the book";
    }

    @PutMapping("/book/{id}")
    public String editBook(@PathVariable Long id) {
        return "Updated Book:" + id;
    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable Long id) {
        return "Book " + id + " was deleted";
    }
}
