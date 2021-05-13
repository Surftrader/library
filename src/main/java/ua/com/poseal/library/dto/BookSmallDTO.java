package ua.com.poseal.library.dto;

import lombok.Data;

@Data
public class BookSmallDTO {

    private Long id;
    private String name;
    private String author;
    private String description;
    private String publisher;
    private String isbn;
    private Integer year;
    private boolean available = true;
}
