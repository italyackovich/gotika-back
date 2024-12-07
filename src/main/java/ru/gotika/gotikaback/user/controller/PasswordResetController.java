package ru.gotika.gotikaback.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public String confirmReset(@RequestParam String email, @RequestParam String code, @RequestParam String newPassword) {
        passwordResetService.validateResetCode(email, code, newPassword);
        return "Пароль успешно изменён.";
    }

}
