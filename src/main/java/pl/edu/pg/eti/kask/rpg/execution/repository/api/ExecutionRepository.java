package pl.edu.pg.eti.kask.rpg.execution.repository.api;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.execution.entity.Execution;
import pl.edu.pg.eti.kask.rpg.repository.api.Repository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface ExecutionRepository extends Repository<Execution, UUID> {


    // Optional<Character> findByIdAndUser(UUID id, User user);

    List<Execution> findAllByUser(User user);

    // List<Character> findAllByProfession(Profession profession);

}
