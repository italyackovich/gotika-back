package ru.gotika.gotikaback.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.gotika.gotikaback.auth.service.JwtService;
import ru.gotika.gotikaback.user.dto.ChangePassword;
import ru.gotika.gotikaback.user.dto.ChangePasswordResponse;
import ru.gotika.gotikaback.user.dto.ConfirmResetPassword;
import ru.gotika.gotikaback.user.dto.RequestResetPassword;
import ru.gotika.gotikaback.user.exception.UserNotFoundException;
import ru.gotika.gotikaback.user.service.PasswordResetService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = PasswordResetController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class,
        }
)
@AutoConfigureMockMvc(addFilters = false)
public class PasswordResetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordResetService passwordResetService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private StringRedisTemplate stringRedisTemplate;

    @MockBean
    private JwtService jwtService;

    @Test
    void requestReset_ShouldReturnChangePasswordResponse_WhenUserExists() throws Exception {
        String requestBody =
                """
                {
                    "email": "john.doe@example.com"
                }
                """;
        ChangePasswordResponse response = new ChangePasswordResponse();
        response.setMessage("Код для сброса пароля отправлен на почту");

        when(passwordResetService.sendResetCode(any(RequestResetPassword.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/users/password-reset/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Код для сброса пароля отправлен на почту"));
    }

    @Test
    void requestReset_ShouldReturnThrowException_WhenUserDoesNotExist() throws Exception {
        String requestBody =
                """
                {
                    "email": "non.existent@example.com"
                }
                """;
        when(passwordResetService.sendResetCode(any(RequestResetPassword.class)))
                .thenThrow(new UserNotFoundException("User with email nonexisting@example.com not found"));

        mockMvc.perform(post("/api/v1/users/password-reset/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void confirmReset_ShouldReturnChangePasswordResponse_WhenCodeExists() throws Exception {
        String requestBody =
                """
                {
                    "email": "john.doe@example.com",
                    "code": "123456"
                }
                """;
        ChangePasswordResponse response = new ChangePasswordResponse();
        response.setSuccess(true);

        when(passwordResetService.validateResetCode(any(ConfirmResetPassword.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/users/password-reset/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void confirmReset_ShouldReturnChangePasswordResponse_WhenCodeDoesNotExistOrInvalidated() throws Exception {
        String requestBody =
                """
                {
                    "email": "john.doe@example.com",
                    "code": "000000"
                }
                """;
        ChangePasswordResponse response = new ChangePasswordResponse();
        response.setSuccess(false);
        response.setMessage("Код для сброса пароля отсутствует или некорректен");

        when(passwordResetService.validateResetCode(any(ConfirmResetPassword.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/users/password-reset/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Код для сброса пароля отсутствует или некорректен"));
    }

    @Test
    void passwordReset_ShouldReturnChangePasswordResponse_WhenUserExistingAndPasswordMatched() throws Exception {
        String requestBody =
                """
                {
                    "email": "john.doe@example.com",
                    "newPassword": "newPassword",
                    "confirmPassword": "newPassword"
                }
                """;
        ChangePasswordResponse response = new ChangePasswordResponse();

        when(passwordResetService.resetPassword(any(ChangePassword.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/users/password-reset/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void passwordReset_ShouldThrowException_WhenUserDoesNotExists() throws Exception {
        String requestBody =
                """
                {
                    "email": "non.existent@example.com",
                    "newPassword": "newPassword",
                    "confirmPassword": "newPassword"
                }
                """;

        when(passwordResetService.resetPassword(any(ChangePassword.class)))
                .thenThrow(new UserNotFoundException("User with email nonexisting@example.com not found"));

        mockMvc.perform(post("/api/v1/users/password-reset/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void passwordReset_ShouldReturnChangePasswordResponse_WhenPasswordsDoNotMatch() throws Exception {
        String requestBody =
                """
                {
                    "email": "non.existent@example.com",
                    "newPassword": "newPassword",
                    "confirmPassword": "newwPassword"
                }
                """;
        ChangePasswordResponse response = new ChangePasswordResponse();
        response.setMessage("Пароли не совпадают");

        when(passwordResetService.resetPassword(any(ChangePassword.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/users/password-reset/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Пароли не совпадают"));
    }

}
