package ua.com.poseal.library.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.poseal.library.models.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmail(String email);
}
