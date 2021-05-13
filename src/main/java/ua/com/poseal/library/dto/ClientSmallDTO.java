package ua.com.poseal.library.dto;

import lombok.Data;

@Data
public class ClientSmallDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
