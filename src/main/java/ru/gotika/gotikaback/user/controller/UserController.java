package ru.gotika.gotikaback.user.controller;

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
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id ,@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @PatchMapping("/{id}/patch")
    public ResponseEntity<UserDto> changeUserAddress(@PathVariable Long id, @RequestBody @Valid ChangeAddress changeAddress) {
        return ResponseEntity.ok(userService.changeUserAddress(id, changeAddress));
    }

    @PatchMapping("/{id}/ch-cred")
    public ResponseEntity<UserDto> changeUserCred(@PathVariable Long id, @RequestBody @Valid ChangeUserCredentials userCredentials){
        System.out.println(userCredentials);
        return ResponseEntity.ok(userService.changeUserCred(id, userCredentials));
    }

    @PatchMapping("/{id}/change-role")
    public ResponseEntity<UserDto> changeRole(@PathVariable Long id, @RequestBody @Valid ChangeRoleDto changeRoleDto) {
        return ResponseEntity.ok(userService.changeRole(id, changeRoleDto));
    }

    @PatchMapping("/{id}/change-img")
    public ResponseEntity<UserDto> changeImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(userService.changeImage(id, file));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
