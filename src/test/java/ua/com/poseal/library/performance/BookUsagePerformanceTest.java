package ua.com.poseal.library.performance;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ua.com.poseal.library.AbstractControllerTest;
import ua.com.poseal.library.models.Book;
import ua.com.poseal.library.models.Client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookUsagePerformanceTest extends AbstractControllerTest {
    
    private final List<Client> clients = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    @BeforeAll
    @SneakyThrows
    public void init() {
        var clientResult = createClients(100);
        var bookResult = createBooks(1000);

        for (String client : clientResult) {
            clients.add(objectMapper.readValue(client, Client.class));
        }

        for (String book : bookResult) {
            books.add(objectMapper.readValue(book, Book.class));
        }

        for (int i = 0; i < clients.size(); i++) {
            var client = clients.get(i);
            for (int j = i * 10; j < i * 10 + 10; j++) {
                var book = books.get(j);
                mockMvc.perform(put("/usage/client/{clientId}/book/{bookId}", client.getId(), book.getId()))
                        .andExpect(status().isOk());
            }
        }
    }

    @AfterAll
    public void clean() {
        clientRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
    void n_plus_one_Expired() throws Exception {
        when(timeService.currentDate()).thenReturn(LocalDate.now().plusDays(15));
        mockMvc.perform(get("/usage/expired"))
                .andExpect(jsonPath("$", hasSize(1000)));
    }
}
