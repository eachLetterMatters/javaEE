package pl.edu.pg.eti.kask.rpg.execution.repository.memory;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.character.repository.api.CharacterRepository;
import pl.edu.pg.eti.kask.rpg.datastore.component.DataStore;
import pl.edu.pg.eti.kask.rpg.execution.entity.Execution;
import pl.edu.pg.eti.kask.rpg.execution.repository.api.ExecutionRepository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
public class ExecutionInMemoryRepository implements ExecutionRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    @Inject
    public ExecutionInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Execution> find(UUID id) {
        return store.findAllExecutions().stream()
                .filter(execution -> execution.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Execution> findAll() {
        return store.findAllExecutions();
    }

    @Override
    public void create(Execution entity) {
        store.createExecution(entity);
    }

    @Override
    public void delete(Execution entity) {
        store.deleteExecution(entity.getId());
    }

    @Override
    public void update(Execution entity) {
        store.updateExecution(entity);
    }

    // @Override
    // public Optional<Character> findByIdAndUser(UUID id, User user) {
    //     return store.findAllCharacters().stream()
    //             .filter(character -> character.getUser().equals(user))
    //             .filter(character -> character.getId().equals(id))
    //             .findFirst();
    // }

    @Override
    public List<Execution> findAllByUser(User user) {
        return store.findAllExecutions().stream()
                .filter(execution -> user.equals(execution.getUser()))
                .collect(Collectors.toList());
    }

    // @Override
    // public List<Character> findAllByProfession(Profession profession) {
    //     return store.findAllCharacters().stream()
    //             .filter(character -> profession.equals(character.getProfession()))
    //             .collect(Collectors.toList());
    // }

}
