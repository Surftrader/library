package ua.com.poseal.library.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.poseal.library.AbstractControllerTest;
import ua.com.poseal.library.dto.ClientDTO;
import ua.com.poseal.library.models.Client;
import ua.com.poseal.library.repositories.ClientRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientControllerTest extends AbstractControllerTest {

    @Autowired
    private ClientRepository clientRepository;

    @AfterEach
    void clearAfter() {
        clientRepository.deleteAll();
    }

    @Test
    void createClient() throws Exception {
        ClientDTO testDTO = mockClient();
        createAndAssert(testDTO);
    }

    @Test
    void getAllClients() throws Exception {
        ClientDTO testDTO1 = mockClient();
        ClientDTO testDTO2 = new ClientDTO();
        testDTO2.setFirstName("John");
        testDTO2.setLastName("Doe");
        testDTO2.setEmail("johnDoe@gmail.com");
        testDTO2.setPhone("+380677654321");

        createAndAssert(testDTO2);
        createAndAssert(testDTO1);

        // get all clients page
        mockMvc.perform(get("/client")
                .param("page", "0")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content.[*].firstName", containsInAnyOrder(testDTO1.getFirstName(), testDTO2.getFirstName())))
                .andExpect(jsonPath("$.content.[*].phone", containsInAnyOrder(testDTO1.getPhone(), testDTO2.getPhone())))
                .andExpect(jsonPath("$.content.[*].bookInUses").doesNotExist());

        // get page with filter by name
        mockMvc.perform(get("/client")
                .param("page", "0")
                .param("size", "10")
                .param("query", testDTO1.getFirstName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].firstName", is(testDTO1.getFirstName())));

        // get page with filter by phone
        mockMvc.perform(get("/client")
                .param("page", "0")
                .param("size", "10")
                .param("query", testDTO1.getPhone()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].phone", is(testDTO1.getPhone())));

    }

    @Test
    void getClient() throws Exception{
        ClientDTO testDTO1 = mockClient();
        Client client = createAndAssert(testDTO1);

        mockMvc.perform(get("/client/" + client.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(client.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(testDTO1.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testDTO1.getLastName())))
                .andExpect(jsonPath("$.email", is(testDTO1.getEmail())))
                .andExpect(jsonPath("$.phone", is(testDTO1.getPhone())));

    }

    @Test
    void editClient() throws Exception{
        ClientDTO clientDTO = mockClient();
        Client client = createAndAssert(clientDTO);

        clientDTO.setFirstName("Mykola");

        mockMvc.perform(put("/client/" + client.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(client.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(clientDTO.getFirstName())));
    }

    @Test
    void deleteClient() throws Exception{
        ClientDTO clientDTO = mockClient();
        Client client = createAndAssert(clientDTO);

        mockMvc.perform(get("/client/" + client.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/client/" + client.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/client/" + client.getId()))
                .andExpect(status().isNotFound());

    }

    @Test
    void createClient_expectBafRequest_whenValidationFailed() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName(null);
        clientDTO.setLastName("");
        clientDTO.setEmail("wrong format");
        clientDTO.setPhone("099-999-99-99");

        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "firstName is mandatory",
                        "lastName is mandatory",
                        "email must match \"^\\S+@\\S+\\.\\S+$\"",
                        "phone must match \"^\\+380\\d{9}$\""
                )));
    }

    @Test
    void createClient_expectConflict_whenClientAlreadyExists() throws Exception {
        ClientDTO clientDTO = mockClient();
        createAndAssert(clientDTO);

        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "Client already exists"
                )));
    }

    private ClientDTO mockClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("Ivan");
        clientDTO.setLastName("Mazepa");
        clientDTO.setEmail("ivan@gmail.com");
        clientDTO.setPhone("+380671234567");

        return clientDTO;
    }

    private Client createAndAssert(ClientDTO testDTO) throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(nullValue())))
                .andExpect(jsonPath("$.firstName", is(testDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(testDTO.getEmail())))
                .andExpect(jsonPath("$.phone", is(testDTO.getPhone())))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Client.class);
    }
}