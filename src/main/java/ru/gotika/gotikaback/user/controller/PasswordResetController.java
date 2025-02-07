package ru.gotika.gotikaback.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.user.dto.ChangePassword;
import ru.gotika.gotikaback.user.dto.ChangePasswordResponse;
import ru.gotika.gotikaback.user.dto.ConfirmResetPassword;
import ru.gotika.gotikaback.user.dto.RequestResetPassword;
import ru.gotika.gotikaback.user.service.PasswordResetService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<ChangePasswordResponse> requestReset(@RequestBody @Valid RequestResetPassword requestResetPassword) {
        return ResponseEntity.ok(passwordResetService.sendResetCode(requestResetPassword));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ChangePasswordResponse> confirmReset(@RequestBody @Valid ConfirmResetPassword confirmResetPassword) {
        return ResponseEntity.ok(passwordResetService.validateResetCode(confirmResetPassword));
    }

    @PostMapping("/reset")
    public ResponseEntity<ChangePasswordResponse> passwordReset(@RequestBody @Valid ChangePassword changePassword) {
        return ResponseEntity.ok(passwordResetService.resetPassword(changePassword));
    }



}
