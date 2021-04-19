package ua.com.poseal.library.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.poseal.library.dto.ClientDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<BookInUse> bookInUses = new ArrayList<>();

    public Client(ClientDTO dto) {
        firstName = dto.getFirstName();
        lastName = dto.getLastName();
        email = dto.getEmail();
        phone = dto.getPhone();
    }
}
