package ru.gotika.gotikaback.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/users/passwords")
@Tag(name = "Password Reset", description = "API for resetting user passwords")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @Operation(
            summary = "Request a password reset code",
            description = "Sends a password reset code to the user's email.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User's email for requesting a reset code",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RequestResetPassword.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"email\": \"user@example.com\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reset code sent successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ChangePasswordResponse.class),
                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            value = "{\"message\": \"Reset code sent successfully.\", \"success\": true}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/requests")
    public ResponseEntity<ChangePasswordResponse> requestReset(@RequestBody @Valid RequestResetPassword requestResetPassword) {
        return ResponseEntity.ok(passwordResetService.sendResetCode(requestResetPassword));
    }

    @Operation(
            summary = "Confirm a password reset code",
            description = "Validates the reset code provided by the user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Reset code and user's email for validation",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConfirmResetPassword.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"email\": \"user@example.com\", \"code\": \"123456\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reset code confirmed successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ChangePasswordResponse.class),
                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            value = "{\"message\": \"Reset code confirmed.\", \"success\": true}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid or expired reset code"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/confirmations")
    public ResponseEntity<ChangePasswordResponse> confirmReset(@RequestBody @Valid ConfirmResetPassword confirmResetPassword) {
        return ResponseEntity.ok(passwordResetService.validateResetCode(confirmResetPassword));
    }

    @Operation(
            summary = "Reset user password",
            description = "Resets the user's password after successful code confirmation.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New password and reset code for password reset",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChangePassword.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"email\": \"user@example.com\", \"code\": \"123456\", \"newPassword\": \"newpassword123\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password reset successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ChangePasswordResponse.class),
                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            value = "{\"message\": \"Password reset successfully.\", \"success\": true}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data or reset code"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/resetting")
    public ResponseEntity<ChangePasswordResponse> passwordReset(@RequestBody @Valid ChangePassword changePassword) {
        return ResponseEntity.ok(passwordResetService.resetPassword(changePassword));
    }
}
