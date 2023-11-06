package pl.edu.pg.eti.kask.rpg.user.controller.simple;

import pl.edu.pg.eti.kask.rpg.user.controller.api.UserController;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.rpg.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.service.UserService;
import pl.edu.pg.eti.kask.rpg.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.rpg.controller.servlet.exception.NotFoundException;

import java.io.InputStream;
import java.util.UUID;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Simple framework agnostic implementation of controller.
 */
@RequestScoped
public class UserSimpleController implements UserController {

    /**
     * User service.
     */
    private final UserService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * @param service User service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public UserSimpleController(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID uuid) {
        return service.find(uuid)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try {
            service.create(factory.requestToUser().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    // @Override
    // public void patchUser(UUID id, PatchUserRequest request) {
    //     service.find(id).ifPresentOrElse(
    //             entity -> service.update(factory.updateUser().apply(entity, request)),
    //             () -> {
    //                 throw new NotFoundException();
    //             }
    //     );
    // }

    // @Override
    // public void deleteUser(UUID id) {
    //     service.find(id).ifPresentOrElse(
    //             entity -> service.delete(id),
    //             () -> {
    //                 throw new NotFoundException();
    //             }
    //     );
    // }

    @Override
    public byte[] getUserPortrait(UUID id) {
        return service.find(id)
                .map(User::getPortrait)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUserPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> service.updatePortrait(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
