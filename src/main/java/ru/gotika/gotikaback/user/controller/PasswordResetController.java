package ru.gotika.gotikaback.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.user.service.PasswordResetService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<String> request(@RequestParam String email) {
        passwordResetService.sendResetCode(email);
        return ResponseEntity.ok("Код сброса отправлен.");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmReset(@RequestParam String email, @RequestParam String code, @RequestParam String newPassword) {
        passwordResetService.validateResetCode(email, code, newPassword);
        return ResponseEntity.ok("Пароль успешно изменён.");
    }

}
