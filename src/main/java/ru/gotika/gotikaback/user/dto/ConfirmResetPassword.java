package ru.gotika.gotikaback.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.common.annotation.ValidResetPasswordCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmResetPassword {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is incorrect")
    private String email;

    @ValidResetPasswordCode
    private String code;
}
