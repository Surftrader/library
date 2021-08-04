package ua.com.poseal.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.com.poseal.library.dto.BookDTO;
import ua.com.poseal.library.dto.ClientDTO;
import ua.com.poseal.library.repositories.BookRepository;
import ua.com.poseal.library.repositories.ClientRepository;
import ua.com.poseal.library.services.TimeService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ClientRepository clientRepository;
    @Autowired
    protected BookRepository bookRepository;

    @MockBean
    protected TimeService timeService;

    protected ClientDTO mockClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("Ivan");
        clientDTO.setLastName("Mazepa");
        clientDTO.setEmail("ivan@gmail.com");
        clientDTO.setPhone("+380671234567");

        return clientDTO;
    }

    @SneakyThrows
    protected ResultActions createClient(ClientDTO testDTO) {
        return mockMvc
                .perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDTO)));
    }

    @SneakyThrows
    protected List<String> createClients(int amount) {
        var result = new ArrayList<String>();

        for (int i = 0; i < amount; i++) {
            var dto = new ClientDTO();
            dto.setFirstName(randomString());
            dto.setLastName(randomString());
            dto.setEmail(dto.getFirstName() + "" + dto.getLastName() + "@gmail.com");
            dto.setPhone("+380" + randomInt(100000000, 999999999));

            var client = createClient(dto).andReturn();
            result.add(client.getResponse().getContentAsString());
        }
        return result;
    }

    @SneakyThrows
    protected List<String> createBooks(int amount) {
        var result = new ArrayList<String>();

        for (int i = 0; i < amount; i++) {
            var dto = new BookDTO();
            dto.setName(randomString());
            dto.setAuthor(randomString());
            dto.setPublisher(randomString());
            dto.setYear(randomInt(1900, 2021));
            dto.setIsbn(String.format("%d-%d-%d-%d-%d",
                    randomInt(100, 999), randomInt(100, 999),
                    randomInt(1000, 9999), randomInt(10, 99), randomInt(1, 9)));

            var book = createBook(dto).andReturn();
            result.add(book.getResponse().getContentAsString());
        }
        return result;
    }

    protected BookDTO mockBook() {
        BookDTO testBook = new BookDTO();
        testBook.setName("Test name");
        testBook.setDescription("Test description");
        testBook.setAuthor("Test author");
        testBook.setPublisher("Test publisher");
        testBook.setYear(2021);
        testBook.setIsbn("978-617-7863-09-9");

        return testBook;
    }

    @SneakyThrows
    protected ResultActions createBook(BookDTO testBook) {
        return mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)));
    }

    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private String randomString() {
        var length = randomInt( 5, 10);
        var builder  = new StringBuilder();

        for (int i = 0; i < length; i++) {
            var position = randomInt( 0, ALPHABET.length() - 1);
            var c = i == 0 ? ALPHABET.toUpperCase().charAt(position) : ALPHABET.charAt(position);
            builder.append(c);
        }
        return builder.toString();
    }
}
