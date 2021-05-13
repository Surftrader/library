package ua.com.poseal.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.library.dto.BookDTO;
import ua.com.poseal.library.dto.BookDetailsDTO;
import ua.com.poseal.library.dto.BookSmallDTO;
import ua.com.poseal.library.mappers.BookMapper;
import ua.com.poseal.library.models.Book;
import ua.com.poseal.library.services.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/book")
    public Page<BookSmallDTO> getAllBooks(@RequestParam Integer page,
                                          @RequestParam Integer size,
                                          @RequestParam(required = false) String query) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookService.getAll(query, pageable);

        final List<BookSmallDTO> dtos = books.get()
                .map(bookMapper::toSmall)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, books.getTotalElements());
    }

    @GetMapping("/book/{id}")
    public BookDetailsDTO getBookById(@PathVariable Long id) {
        return bookMapper.toDetails(bookService.get(id));
    }

    @PostMapping("/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookSmallDTO createBook(@Valid @RequestBody BookDTO bookDTO) {
        return bookMapper.toSmall(bookService.create(bookDTO));
    }

    @PutMapping("/book/{id}")
    public BookDetailsDTO editBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        return bookMapper.toDetails(bookService.update(id, bookDTO));
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
