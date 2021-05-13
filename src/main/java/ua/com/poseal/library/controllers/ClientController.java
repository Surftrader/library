package ua.com.poseal.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.library.dto.ClientDTO;
import ua.com.poseal.library.dto.ClientDetailsDTO;
import ua.com.poseal.library.dto.ClientSmallDTO;
import ua.com.poseal.library.mappers.ClientMapper;
import ua.com.poseal.library.models.Client;
import ua.com.poseal.library.services.ClientService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping("/client")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client createClient(@Valid @RequestBody ClientDTO dto) {
        return clientService.create(dto);
    }

    @GetMapping("/client/{id}")
    public ClientDetailsDTO getClient(@PathVariable Long id) {
        return clientMapper.toDetails(clientService.get(id));
    }

    @GetMapping("/client")
    public Page<ClientSmallDTO> getAllClients(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String query) {
        Pageable pageable = PageRequest.of(page, size);
        final Page<Client> clients = clientService.getAll(query, pageable);

        final List<ClientSmallDTO> dtos = clients.get()
                .map(clientMapper::toSmall)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, clients.getTotalElements());
    }

    @PutMapping("/client/{id}")
    public ClientDetailsDTO editClient(@PathVariable Long id,
                             @Valid @RequestBody ClientDTO dto) {
        return clientMapper.toDetails(clientService.update(id, dto));
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }
}
