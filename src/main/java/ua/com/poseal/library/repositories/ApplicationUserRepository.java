package ua.com.poseal.library.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.com.poseal.library.models.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmail(String email);
}
