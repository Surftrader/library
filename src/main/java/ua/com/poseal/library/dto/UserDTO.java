package ua.com.poseal.library.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private boolean locked;
    private boolean enable;

}
