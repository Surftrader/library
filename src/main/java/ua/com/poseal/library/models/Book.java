package ua.com.poseal.library.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.poseal.library.dto.BookDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author;
    private String description;
    private String publisher;
    private String isbn;
    private Integer year;

    private boolean available = true;
    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    private List<BookHistory> bookHistories = new ArrayList<>();

    public Book(BookDTO bookDTO) {

        this.name = bookDTO.getName();
        this.author = bookDTO.getAuthor();
        this.description = bookDTO.getDescription();
        this.publisher = bookDTO.getPublisher();
        this.isbn = bookDTO.getIsbn();
        this.year = bookDTO.getYear();
    }
}
