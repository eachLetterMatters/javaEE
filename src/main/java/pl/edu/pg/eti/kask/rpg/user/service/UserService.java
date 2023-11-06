package pl.edu.pg.eti.kask.rpg.user.service;

import pl.edu.pg.eti.kask.rpg.crypto.component.Pbkdf2PasswordHash;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Service layer for all business actions regarding user entity.
 */
@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserService {

    /**
     * Repository for user entity.
     */
    private final UserRepository repository;

    /**
     * Hash mechanism used for storing users' passwords.
     */
    private final Pbkdf2PasswordHash passwordHash;

    /**
     * @param repository   repository for character entity
     * @param passwordHash hash mechanism used for storing users' passwords
     */
    @Inject
    public UserService(UserRepository repository, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    /**
     * @param id user's id
     * @return container (can be empty) with user
     */
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * Seeks for single user using login and password. Can be used in authentication module.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    /**
     * Saves new user. Password is hashed using configured hash algorithm.
     *
     * @param user new user to be saved
     */
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    /**
     * @param login    user's login
     * @param password user's password
     * @return true if provided login and password are correct
     */
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

    public void updatePortrait(UUID id, InputStream is) {
        repository.find(id).ifPresent(user -> {
            try {

                byte[] temp_portrait = is.readAllBytes();

                user.setPortrait(temp_portrait);
                
                // Path path = Paths.get("src/main/resources/" + id.toString());
                // Files.write(path, temp_portrait);

                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    


}
