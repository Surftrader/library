package ua.com.poseal.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookHistoryDTO {
    private ClientSmallDTO client;
    private LocalDate inUseFrom;
    private LocalDate inUseTo;
}
