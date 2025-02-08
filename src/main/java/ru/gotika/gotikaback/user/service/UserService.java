package ru.gotika.gotikaback.user.service;

import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.user.dto.ChangeAddress;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.ChangeUserCredentials;
import ru.gotika.gotikaback.user.dto.UserDto;

import java.util.List;

public interface UserService {
    /**
     * Gets all users from database.
     *
     * @return a {@code List}<{@link UserDto}> containing list all users.
     */
    List<UserDto> getAllUsers();

    /**
     * Gets user from database by {@code id}.
     *
     * @param id containing user's id.
     *           Must not be a null.
     * @return an {@link UserDto} containing user's data.
     */
    UserDto getUser(Long id);

    /**
     * Creates a new user based on the provided {@link UserDto}.
     *
     * @param userDto containing user's data.
     *                Must not be a null.
     * @return an {@link UserDto} containing created user's data.
     */
    UserDto createUser(UserDto userDto);

    /**
     * Updates user by {@code id} and {@link UserDto}.
     *
     * @param id containing user's id.
     *           Must not be a null.
     * @param userDto containing user's data.
     *                Must not be a null.
     * @return an {@link UserDto} containing updated user's data.
     */
    UserDto updateUser(Long id, UserDto userDto);

    /**
     * Changes user's address by {@code id} and {@link ChangeAddress}.
     *
     * @param id containing user's id.
     *           Must not be a null.
     * @param changeAddress containing new user's address.
     *                      Must not be a null.
     * @return an {@link UserDto} containing user's data with updated address.
     */
    UserDto changeUserAddress(Long id, ChangeAddress changeAddress);

    /**
     * Changes user's role by {@code id} and {@link ChangeRoleDto}.
     *
     * @param id containing user's id.
     *           Must not be a null.
     * @param changeRoleDto containing new user's role.
     *                      Must not be a null.
     * @return an {@link UserDto} containing user's data with updated role.
     */
    UserDto changeRole(Long id, ChangeRoleDto changeRoleDto);

    /**
     * Changes user's image by {@code id} and {@link MultipartFile}.
     *
     * @param id containing user's id.
     *           Must not be a null.
     * @param image containing new user's image.
     * @return an {@link UserDto} containing user's data with updated image.
     */
    UserDto changeImage(Long id, MultipartFile image);

    /**
     * Changes user's credentials by {@code id} and {@link ChangeUserCredentials}.
     *
     * @param id containing user's id.
     *           Must not be a null.
     * @param userCredentials containing new user's credentials.
     *                        Must not be a null.
     * @return an {@link UserDto} containing user's data with updated credentials.
     */
    UserDto changeUserCred(Long id, ChangeUserCredentials userCredentials);

    /**
     * Deletes user by {@code id}.
     *
     * @param id containing user's id.
     *           Must not be a null.
     */
    void deleteUser(Long id);
}
