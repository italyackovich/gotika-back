package ru.gotika.gotikaback.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.user.dto.ChangeAddress;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id ,@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @PatchMapping("/{id}/patch")
    public ResponseEntity<UserDto> patchUser(@PathVariable Long id, @RequestBody ChangeAddress changeAddress) {
        return ResponseEntity.ok(userService.patchUser(id, changeAddress));
    }

    @PatchMapping("/{id}/change-role")
    public ResponseEntity<UserDto> changeRole(@PathVariable Long id, @RequestBody ChangeRoleDto changeRoleDto) {
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
