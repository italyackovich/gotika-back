package ru.gotika.gotikaback.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.common.service.CodeStorageService;
import ru.gotika.gotikaback.common.service.EmailService;
import ru.gotika.gotikaback.common.util.CodeGenerator;
import ru.gotika.gotikaback.user.dto.ChangePassword;
import ru.gotika.gotikaback.user.dto.ChangePasswordResponse;
import ru.gotika.gotikaback.user.dto.ConfirmResetPassword;
import ru.gotika.gotikaback.user.dto.RequestResetPassword;
import ru.gotika.gotikaback.user.exception.UserNotFoundException;
import ru.gotika.gotikaback.user.model.User;
import ru.gotika.gotikaback.user.repository.UserRepository;
import ru.gotika.gotikaback.user.service.PasswordResetService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {

    private final CodeStorageService codeStorageService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public ChangePasswordResponse sendResetCode(RequestResetPassword requestResetPassword) {
        String email = requestResetPassword.getEmail();
        if (!userRepository.existsByEmail(email)) {
            log.error("User with email {} not found", email);
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        String code = CodeGenerator.generateCode();
        codeStorageService.saveCode(email, code);
        emailService.sendEmail(email, "Сброс пароля", "Ваш код для сброса пароля: " + code);
        return ChangePasswordResponse.builder().message("Код для сброса пароля отправлен на почту").build();
    }

    @Override
    public ChangePasswordResponse validateResetCode(ConfirmResetPassword confirmResetPassword) {
        String email = confirmResetPassword.getEmail();
        String code = confirmResetPassword.getCode();
        String storedCode = codeStorageService.getCode(email);
        if (storedCode == null || !storedCode.equals(code)) {
            log.warn("Reset password code is not valid for user with email {}", email);
            return ChangePasswordResponse.builder().message("Код для сброса пароля отсутствует или некорректен").success(false).build();
        }
        return ChangePasswordResponse.builder().success(true).build();
    }

    @Override
    public ChangePasswordResponse resetPassword(ChangePassword changePassword) {
        String email = changePassword.getEmail();
        String newPassword = changePassword.getNewPassword();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        if (!changePassword.newPasswordEqualsConfirmPassword()) {
            log.warn("Reset password confirmation password does not match");
            return ChangePasswordResponse.builder().message("Пароли не совпадают").build();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        codeStorageService.deleteCode(email);
        return ChangePasswordResponse.builder().build();
    }
}
