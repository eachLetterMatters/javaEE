package pl.edu.pg.eti.kask.rpg.character.service;

import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.character.repository.api.ProfessionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service layer for all business actions regarding character's profession entity.
 */
@ApplicationScoped
@NoArgsConstructor(force = true)
public class ProfessionService {

    /**
     * Repository for profession entity.
     */
    private final ProfessionRepository repository;

    /**
     * @param repository repository for profession entity
     */
    @Inject
    public ProfessionService(ProfessionRepository repository) {
        this.repository = repository;
    }

    /**
     * @param id profession's id
     * @return container with profession entity
     */
    public Optional<Profession> find(UUID id) {
        return repository.find(id);
    }

    /**
     * @return all available professions
     */
    public List<Profession> findAll() {
        return repository.findAll();
    }

    /**
     * Stores new profession in the data store.
     *
     * @param profession new profession to be saved
     */
    public void create(Profession profession) {
        repository.create(profession);
    }

}
