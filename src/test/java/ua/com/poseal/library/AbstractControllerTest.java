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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
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
}
