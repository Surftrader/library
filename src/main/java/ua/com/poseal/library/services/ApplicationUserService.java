package ua.com.poseal.library.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.poseal.library.exeptions.ConflictException;
import ua.com.poseal.library.exeptions.NotFoundException;
import ua.com.poseal.library.models.ApplicationUser;
import ua.com.poseal.library.repositories.ApplicationUserRepository;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    public Page<ApplicationUser> getAll(Pageable pageable) {
        return applicationUserRepository.findAll(pageable);
    }

    public ApplicationUser get(Long id) {
        return applicationUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public ApplicationUser create(ApplicationUser applicationUser) {
        var userFromDB = applicationUserRepository.findByEmail(applicationUser.getEmail());
        if(userFromDB.isPresent()) {
            throw new ConflictException("User already exists");
        }
        var created = applicationUserRepository.save(applicationUser);

        log.info("Created new user {}", applicationUser);

        return created;
    }

    public ApplicationUser update(Long id, ApplicationUser user) {

        ApplicationUser userFromDB = get(id);
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        String email = user.getEmail();
        //TODO: check email (unique)
        userFromDB.setEmail(email);
        userFromDB.setPassword(user.getPassword());

        return applicationUserRepository.save(userFromDB);
    }

    public void delete(Long id) {
        var userFromDB = get(id);
        userFromDB.setEnable(false);
        applicationUserRepository.save(userFromDB);

        log.info("User {} was disabled", userFromDB);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var optional = applicationUserRepository.findByEmail(username);

        if(optional.isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        var applicationUser = optional.get();

        return new User(
                applicationUser.getEmail(),
                applicationUser.getPassword(),
                applicationUser.isEnable(),
                true,
                true,
                !applicationUser.isLocked(),
                new HashSet<>()
        );
    }
}
