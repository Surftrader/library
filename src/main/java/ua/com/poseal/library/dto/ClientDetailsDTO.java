package ua.com.poseal.library.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientDetailsDTO extends ClientSmallDTO {

    private List<BookInUseDTO> bookInUses;
}
