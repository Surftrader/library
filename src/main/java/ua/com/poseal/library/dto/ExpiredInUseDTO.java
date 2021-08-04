package ua.com.poseal.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpiredInUseDTO {

    private ClientSmallDTO client;
    private BookSmallDTO book;
    private LocalDate inUseFrom;
    private Long expiredDays;
}
