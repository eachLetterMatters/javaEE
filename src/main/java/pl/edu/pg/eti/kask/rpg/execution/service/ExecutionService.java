package pl.edu.pg.eti.kask.rpg.execution.service;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.repository.api.CharacterRepository;
import pl.edu.pg.eti.kask.rpg.character.repository.api.ProfessionRepository;
import pl.edu.pg.eti.kask.rpg.execution.entity.Execution;
import pl.edu.pg.eti.kask.rpg.execution.repository.api.ExecutionRepository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;


@ApplicationScoped
@NoArgsConstructor(force = true)
public class ExecutionService {

    private final ExecutionRepository executionRepository;
    private final UserRepository userRepository;

    @Inject
    public ExecutionService(ExecutionRepository executionRepository, UserRepository userRepository) {
        this.executionRepository = executionRepository;
        this.userRepository = userRepository;
    }

    public Optional<Execution> find(UUID id) {
        return executionRepository.find(id);
    }

    // public Optional<Character> find(User user, UUID id) {
    //     return characterRepository.findByIdAndUser(id, user);
    // }

    public List<Execution> findAll() {
        return executionRepository.findAll();
    }

    // public List<Character> findAll(User user) {
    //     return characterRepository.findAllByUser(user);
    // }

    public void create(Execution execution) {
        executionRepository.create(execution);
    }

    public void update(Execution execution) {
        executionRepository.update(execution);
    }

    public void delete(UUID id) {
        executionRepository.delete(executionRepository.find(id).orElseThrow());
    }


    // public Optional<List<Character>> findAllByProfession(UUID id) {
    //     return professionRepository.find(id)
    //             .map(characterRepository::findAllByProfession);
    // }

    public Optional<List<Execution>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(executionRepository::findAllByUser);
    }
}
