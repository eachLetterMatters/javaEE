package pl.edu.pg.eti.kask.rpg.user.controller.api;

import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.rpg.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

/**
 * Controller for managing collections users' representations.
 */
public interface UserController {

    /**
     * @return all users representation
     */
    GetUsersResponse getUsers();

    /**
     * @param uuid user's id
     * @return user representation
     */
    GetUserResponse getUser(UUID uuid);

    /**
     * @param id      user's id
     * @param request new user representation
     */
    void putUser(UUID id, PutUserRequest request);

    /**
     * @param id      user's id
     * @param request user update representation
     */
    // void patchUser(UUID id, PatchUserRequest request);

    /**
     * @param id user's id
     */
    // void deleteUser(UUID id);

    /**
     * @param id user's id
     * @return user's portrait
     */
    byte[] getUserPortrait(UUID id);

    /**
     * @param id       user's id
     * @param portrait user's new avatar
     */
    void putUserPortrait(UUID id, InputStream portrait);

}
