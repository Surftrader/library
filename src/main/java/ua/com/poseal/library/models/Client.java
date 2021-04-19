package ua.com.poseal.library.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.poseal.library.dto.ClientDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Client(ClientDTO dto) {
        firstName = dto.getFirstName();
        lastName = dto.getLastName();
        email = dto.getEmail();
        phone = dto.getPhone();
    }
}
