package ru.gotika.gotikaback.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.user.dto.ChangePasswordRequest;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.enums.Role;
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PutMapping("/{id}/put")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id ,@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @PatchMapping("/{id}/change-role")
    public ResponseEntity<UserDto> changeRole(@PathVariable Long id, @RequestBody ChangeRoleDto changeRoleDto) {
        return ResponseEntity.ok(userService.changeRole(id, changeRoleDto));
    }

//    @PatchMapping("/chpassw")
//    public ResponseEntity<UserDto> changePassword(@RequestBody ChangePasswordRequest passwordRequest) {
//        //todo
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
//        //todo
//    }

}
