package ua.com.poseal.library.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.poseal.library.AbstractControllerTest;
import ua.com.poseal.library.dto.BookDTO;
import ua.com.poseal.library.models.Book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends AbstractControllerTest {

    @Test
    void createBook() throws Exception {
        BookDTO testBook = mockBook();

        MvcResult mvcResult =
                createBook(testBook)
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();

        Book bookResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Book.class);
        assertThat(bookResult.getId(), notNullValue());
        assertThat(bookResult.getName(), equalTo(testBook.getName()));
        assertThat(bookResult.getAuthor(), equalTo(testBook.getAuthor()));
        assertThat(bookResult.getDescription(), equalTo(testBook.getDescription()));
        assertThat(bookResult.getPublisher(), equalTo(testBook.getPublisher()));
        assertThat(bookResult.getYear(), equalTo(testBook.getYear()));
        assertThat(bookResult.getIsbn(), equalTo(testBook.getIsbn()));
    }

    @Test
    void findBook() throws Exception {
        BookDTO testBook = mockBook();

        createBook(testBook)
                .andDo(print())
                .andExpect(status().isCreated());

        String unknownAuthor = "qwert";
        mockMvc.perform(get("/book")
                .param("page", "0")
                .param("size", "1")
                .param("query", unknownAuthor))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content", empty()));

        String knownAuthor = "author";
        mockMvc.perform(get("/book")
                .param("page", "0")
                .param("size", "1")
                .param("query", knownAuthor))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].bookHistories").doesNotExist());
    }

    @Test
    void createBookValidation() throws Exception {
        BookDTO testBook = mockBook();
        testBook.setName(null);
        testBook.setAuthor("");

        createBook(testBook)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder("name is mandatory", "author is mandatory")));

        testBook = mockBook();
        testBook.setIsbn("wrong format");

        createBook(testBook)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder("isbn must match \"^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$\"")));

    }

    @Test
    void getNotExistingBook() throws Exception {
        int nonexistentId = 321;
        mockMvc.perform(get("/book/" + nonexistentId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.errors", contains("Book not found")));
    }
}