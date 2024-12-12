package ru.gotika.gotikaback.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.common.service.CodeStorageService;
import ru.gotika.gotikaback.common.service.EmailService;
import ru.gotika.gotikaback.common.util.CodeGenerator;
import ru.gotika.gotikaback.user.repository.UserRepository;
import ru.gotika.gotikaback.user.service.PasswordResetService;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final CodeStorageService codeStorageService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void sendResetCode(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Пользователь с таким email не найден.");
        }
        String code = CodeGenerator.generateCode();
        codeStorageService.saveCode(email, code);
        emailService.sendEmail(email, "Сброс пароля", "Ваш код для сброса пароля: " + code);
    }

    @Override
    public void validateResetCode(String email, String code, String newPassword) {
        String storedCode = codeStorageService.getCode(email);
        if (storedCode == null || !storedCode.equals(code)) {
            throw new IllegalArgumentException("Неверный или истекший код.");
        }
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден."));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        codeStorageService.deleteCode(email);
    }

    @Override
    public void resetPassword(String email, String newPassword) {
    }
}
