package ua.com.poseal.library.dto;

import lombok.Data;
import ua.com.poseal.library.models.BookHistory;

import java.util.List;

@Data
public class BookDetailsDTO extends BookSmallDTO {
    private List<BookHistoryDTO> bookHistories;
}
