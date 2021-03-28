package ua.com.poseal.library.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class BookDTO {

    @NotBlank(message = "is mandatory")
    private String name;

    @NotBlank(message = "is mandatory")
    private String author;
    private String description;
    private String publisher;

    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")
    private String isbn;

    @Range(min = 1200, max = 3000)
    private Integer year;

}
