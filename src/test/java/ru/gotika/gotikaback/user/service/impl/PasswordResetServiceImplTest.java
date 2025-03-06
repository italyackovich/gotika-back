package ru.gotika.gotikaback.user.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PasswordResetServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private CodeStorageService codeStorageService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordResetServiceImpl passwordResetService;

    @Test
    void sendResetCode_ShouldReturnChangePasswordResponse_IfUserExists() {
        String email = "john.doe@gmail.com";
        RequestResetPassword request = new RequestResetPassword();
        request.setEmail(email);

        when(userRepository.existsByEmail(email)).thenReturn(true);

        try (MockedStatic<CodeGenerator> codeGenMock = mockStatic(CodeGenerator.class)) {
            String generatedCode = "123456";
            codeGenMock.when(CodeGenerator::generateCode).thenReturn(generatedCode);

            ChangePasswordResponse response = passwordResetService.sendResetCode(request);

            verify(codeStorageService).saveCode(email, generatedCode);
            verify(emailService).sendEmail(eq(email), eq("Сброс пароля"), contains(generatedCode));

            assertEquals("Код для сброса пароля отправлен на почту", response.getMessage());
        }
    }

    @Test
    void sendResetCode_ShouldThrowException_IfUserDoesNotExist() {
        String email = "non.existent@gmail.com";
        RequestResetPassword request = new RequestResetPassword();
        request.setEmail(email);

        when(userRepository.existsByEmail(email)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> passwordResetService.sendResetCode(request));

        verifyNoInteractions(emailService);
        verifyNoInteractions(codeStorageService);
    }

    @Test
    void validateResetCode_ShouldReturnChangePasswordResponse_IfCodeExisting() {
        String email = "john.doe@gmail.com";
        String code = "123456";
        ConfirmResetPassword confirmResetPassword = new ConfirmResetPassword();
        confirmResetPassword.setEmail(email);
        confirmResetPassword.setCode(code);

        when(codeStorageService.getCode(email)).thenReturn(code);

        ChangePasswordResponse response = passwordResetService.validateResetCode(confirmResetPassword);

        assertTrue(response.isSuccess());
    }

    @Test
    void validateResetCode_ShouldReturnChangePasswordResponse_IfCodeDoesNotExist() {
        String email = "john.doe@gmail.com";
        String code = "123456";
        ConfirmResetPassword confirmResetPassword = new ConfirmResetPassword();
        confirmResetPassword.setEmail(email);
        confirmResetPassword.setCode(code);

        when(codeStorageService.getCode(email)).thenReturn(null);

        ChangePasswordResponse response = passwordResetService.validateResetCode(confirmResetPassword);
        assertFalse(response.isSuccess());
        assertEquals("Код для сброса пароля отсутствует или некорректен", response.getMessage());
    }

    @Test
    void validateResetCode_ShouldReturnChangePasswordResponse_IfCodeInvalidated() {
        String email = "john.doe@gmail.com";
        String requestCode = "123456";
        String storedCode = "654321";
        ConfirmResetPassword confirmResetPassword = new ConfirmResetPassword();
        confirmResetPassword.setEmail(email);
        confirmResetPassword.setCode(requestCode);

        when(codeStorageService.getCode(email)).thenReturn(storedCode);

        ChangePasswordResponse response = passwordResetService.validateResetCode(confirmResetPassword);

        assertFalse(response.isSuccess());
        assertEquals("Код для сброса пароля отсутствует или некорректен", response.getMessage());
    }

    @Test
    void resetPassword_ShouldReturnChangePasswordResponse_IfUserExistingAndPasswordsMatched() {
        String email = "john.doe@gmail.com";
        String newPassword = "123456";
        String confirmPassword = "123456";

        User user = new User();
        user.setEmail(email);
        user.setPassword("oldPassword");

        ChangePassword changePassword = new ChangePassword();
        changePassword.setEmail(email);
        changePassword.setNewPassword(newPassword);
        changePassword.setConfirmPassword(confirmPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        when(userRepository.save(user)).thenAnswer(invocation -> invocation.getArgument(0));

        ChangePasswordResponse response = passwordResetService.resetPassword(changePassword);

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).encode(newPassword);
        verify(userRepository).save(user);
        verify(codeStorageService).deleteCode(email);

        assertNotNull(response);
    }

    @Test
    void resetPassword_ShouldThrowException_IfUserDoesNotExist() {
        String email = "non.existent@gmail.com";
        String newPassword = "123456";
        String confirmPassword = "123456";

        ChangePassword changePassword = new ChangePassword();
        changePassword.setEmail(email);
        changePassword.setNewPassword(newPassword);
        changePassword.setConfirmPassword(confirmPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> passwordResetService.resetPassword(changePassword));

        verify(userRepository).findByEmail(email);
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(codeStorageService);
    }

    @Test
    void resetPassword_ShouldReturnChangePasswordResponse_IfPasswordsDoNotMatch() {
        String email = "john.doe@gmail.com";
        String newPassword = "123456";
        String confirmPassword = "654321";

        User user = new User();
        user.setEmail(email);
        user.setPassword("oldPassword");

        ChangePassword changePassword = new ChangePassword();
        changePassword.setEmail(email);
        changePassword.setNewPassword(newPassword);
        changePassword.setConfirmPassword(confirmPassword);

        ChangePassword spyChangePassword = spy(changePassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(spyChangePassword.newPasswordEqualsConfirmPassword()).thenReturn(false);

        ChangePasswordResponse response = passwordResetService.resetPassword(spyChangePassword);

        verify(userRepository).findByEmail(email);
        verify(spyChangePassword).newPasswordEqualsConfirmPassword();
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(codeStorageService);

        assertEquals("Пароли не совпадают", response.getMessage());
    }

}
