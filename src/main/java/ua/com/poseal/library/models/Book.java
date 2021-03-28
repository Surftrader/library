package ua.com.poseal.library.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.poseal.library.dto.BookDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public Book(BookDTO bookDTO) {

        this.name = bookDTO.getName();
        this.author = bookDTO.getAuthor();
        this.description = bookDTO.getDescription();
        this.publisher = bookDTO.getPublisher();
        this.isbn = bookDTO.getIsbn();
        this.year = bookDTO.getYear();
    }
}
