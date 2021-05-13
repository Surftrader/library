package ua.com.poseal.library.mappers;

import org.mapstruct.Mapper;
import ua.com.poseal.library.dto.BookDetailsDTO;
import ua.com.poseal.library.dto.BookSmallDTO;
import ua.com.poseal.library.models.Book;

@Mapper
public interface BookMapper {
    BookSmallDTO toSmall(Book book);
    BookDetailsDTO toDetails(Book book);
}
