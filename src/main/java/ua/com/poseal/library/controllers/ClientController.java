package ua.com.poseal.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.library.dto.ClientDTO;
import ua.com.poseal.library.models.Client;
import ua.com.poseal.library.services.ClientService;

import javax.validation.Valid;

@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client createClient(@Valid @RequestBody ClientDTO dto) {
        return clientService.create(dto);
    }

    @GetMapping("/client/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.get(id);
    }

    @GetMapping("/client")
    public Page<Client> getAllClients(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String query) {
        Pageable pageable = PageRequest.of(page, size);

        return clientService.getAll(query, pageable);
    }

    @PutMapping("/client/{id}")
    public Client editClient(@PathVariable Long id,
                             @Valid @RequestBody ClientDTO dto) {
        return clientService.update(id, dto);
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }
}
