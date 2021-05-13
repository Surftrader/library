package ua.com.poseal.library.dto;

import lombok.Data;
import ua.com.poseal.library.models.Book;

import java.time.LocalDate;

@Data
public class BookInUseDTO {
    // TODO: replace entity by DTO
    private Book book;
    private LocalDate inUseFrom;
}
