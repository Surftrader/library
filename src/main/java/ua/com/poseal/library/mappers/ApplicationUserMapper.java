package ua.com.poseal.library.mappers;

import lombok.Getter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.poseal.library.dto.CreateUserDTO;
import ua.com.poseal.library.dto.UserDTO;
import ua.com.poseal.library.models.ApplicationUser;

@Mapper
@Getter
public abstract class ApplicationUserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract UserDTO toDTO(ApplicationUser applicationUser);

    @Mapping(target = "password", ignore = true)
    public abstract ApplicationUser toApplicationUser(CreateUserDTO dto);

    @AfterMapping
    void map(@MappingTarget ApplicationUser applicationUser, CreateUserDTO userDTO) {
        applicationUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }
}
