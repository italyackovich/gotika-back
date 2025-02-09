package ru.gotika.gotikaback.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.user.dto.ChangeAddress;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.ChangeUserCredentials;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Get all users",
            description = "Returns a list of all users in the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of users",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Get user by ID",
            description = "Returns a user by their unique identifier.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User found",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user based on the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User created successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @Operation(
            summary = "Update user",
            description = "Updates an existing user with the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id ,@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @Operation(
            summary = "Change user address",
            description = "Updates the address of an existing user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New address data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Address updated successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @PatchMapping("/{id}/ch-address")
    public ResponseEntity<UserDto> changeUserAddress(@PathVariable Long id, @RequestBody @Valid ChangeAddress changeAddress) {
        return ResponseEntity.ok(userService.changeAddress(id, changeAddress));
    }

    @Operation(
            summary = "Change user credentials",
            description = "Updates the credentials (e.g., password) of an existing user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New credentials data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ChangeUserCredentials.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Credentials updated successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @PatchMapping("/{id}/ch-cred")
    public ResponseEntity<UserDto> changeUserCred(@PathVariable Long id, @RequestBody @Valid ChangeUserCredentials userCredentials){
        System.out.println(userCredentials);
        return ResponseEntity.ok(userService.changeCred(id, userCredentials));
    }

    @Operation(
            summary = "Change user role",
            description = "Updates the role of an existing user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New role data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ChangeRoleDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Role updated successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @PatchMapping("/{id}/ch-role")
    public ResponseEntity<UserDto> changeRole(@PathVariable Long id, @RequestBody @Valid ChangeRoleDto changeRoleDto) {
        return ResponseEntity.ok(userService.changeRole(id, changeRoleDto));
    }

    @Operation(
            summary = "Change user profile image",
            description = "Updates the profile image of an existing user.",
            parameters = {
                    @Parameter(
                            name = "file",
                            description = "Image file to upload",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image updated successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid file or input data"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PatchMapping("/{id}/ch-image")
    public ResponseEntity<UserDto> changeImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(userService.changeImage(id, file));
    }

    @Operation(
            summary = "Delete user",
            description = "Deletes an existing user by their ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
