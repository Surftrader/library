package ua.com.poseal.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class BookInUse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Client client;
    @ManyToOne
    private Book book;

    private LocalDate inUseFrom = LocalDate.now();

}
