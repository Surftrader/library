package ua.com.poseal.library.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.poseal.library.dto.ClientDTO;
import ua.com.poseal.library.exeptions.ConflictException;
import ua.com.poseal.library.exeptions.NotFoundException;
import ua.com.poseal.library.models.Client;
import ua.com.poseal.library.repositories.ClientRepository;

import java.util.Optional;

@Slf4j
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client create(ClientDTO dto) {
        Optional<Client> optional = clientRepository.findByEmailOrPhone(dto.getEmail(), dto.getPhone());

        if (optional.isPresent()) {
            throw new ConflictException("Client already exists");
        }

        Client client = new Client(dto);
        Client saved = clientRepository.save(client);
        log.info("Client {} was created", saved);

        return saved;
    }

    public Page<Client> getAll(String query, Pageable pageable) {
        if (query != null) {
            return clientRepository.findByQuery("%" + query.toLowerCase() + "%", pageable);
        }
        return clientRepository.findAll(pageable);
    }

    public Client get(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
    }

    public Client update(Long id, ClientDTO dto){
        Client client = get(id);
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());

        return clientRepository.save(client);
    }

    public void delete(Long id) {
        Client client = get(id);
        clientRepository.delete(client);
        log.info("Client {} was successfully deleted", client);
    }
}
