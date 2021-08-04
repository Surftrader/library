package ua.com.poseal.library.controllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.library.dto.UserDTO;
import ua.com.poseal.library.dto.CreateUserDTO;
import ua.com.poseal.library.mappers.ApplicationUserMapper;
import ua.com.poseal.library.services.ApplicationUserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;
    private final ApplicationUserMapper applicationUserMapper;

    @GetMapping("/user")
    public Page<UserDTO> getAllUsers(
            @RequestParam Integer page,
            @RequestParam Integer size) {

        var pageable = PageRequest.of(page, size);
        var applicationUsers = applicationUserService.getAll(pageable);

        var dtos = applicationUsers.get()
                .map(applicationUserMapper::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, applicationUsers.getTotalElements());
    }

    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return applicationUserMapper.toDTO(applicationUserService.get(id));
    }

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        var user = applicationUserService.create(applicationUserMapper.toApplicationUser(createUserDTO));
        return applicationUserMapper.toDTO(user);
    }

    @PutMapping("/user/{id}")
    public UserDTO editUser(@PathVariable Long id, @RequestBody @Valid CreateUserDTO userDTO) {
        return applicationUserMapper.toDTO(applicationUserService.update(id, applicationUserMapper.toApplicationUser(userDTO)));
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        applicationUserService.delete(id);
    }
}
