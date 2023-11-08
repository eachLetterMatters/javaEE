package pl.edu.pg.eti.kask.rpg.execution.controller.simple;

import pl.edu.pg.eti.kask.rpg.character.controller.api.CharacterController;
import pl.edu.pg.eti.kask.rpg.character.dto.GetCharacterResponse;
import pl.edu.pg.eti.kask.rpg.character.dto.GetCharactersResponse;
import pl.edu.pg.eti.kask.rpg.character.dto.PatchCharacterRequest;
import pl.edu.pg.eti.kask.rpg.character.dto.PutCharacterRequest;
import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.service.CharacterService;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.rpg.execution.controller.api.ExecutionController;
import pl.edu.pg.eti.kask.rpg.execution.dto.GetExecutionResponse;
import pl.edu.pg.eti.kask.rpg.execution.dto.GetExecutionsResponse;
import pl.edu.pg.eti.kask.rpg.execution.dto.PutExecutionRequest;
import pl.edu.pg.eti.kask.rpg.execution.service.ExecutionService;

import java.io.InputStream;
import java.util.UUID;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Simple framework agnostic implementation of controller.
 */
@RequestScoped
public class ExecutionSimpleController implements ExecutionController {


    private final ExecutionService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    @Inject
    public ExecutionSimpleController(ExecutionService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetExecutionsResponse getExecutions() {
        return factory.executionsToResponse().apply(service.findAll());
    }

    // @Override
    // public GetCharactersResponse getProfessionCharacters(UUID id) {
    //     return service.findAllByProfession(id)
    //             .map(factory.charactersToResponse())
    //             .orElseThrow(NotFoundException::new);
    // }

    @Override
    public GetExecutionsResponse getUserExecutions(UUID id) {
        return service.findAllByUser(id)
                .map(factory.executionsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetExecutionResponse getExecution(UUID uuid) {
        return service.find(uuid)
                .map(factory.executionToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putExecution(UUID id, PutExecutionRequest request) {
        try {
            service.create(factory.requestToExecution().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);

        }
    }

    @Override
    public void deleteExecution(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
