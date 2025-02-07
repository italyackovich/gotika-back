package ru.gotika.gotikaback.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.common.annotation.ValidPassword;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is incorrect")
    private String email;

    @ValidPassword
    private String newPassword;

    @ValidPassword
    private String confirmPassword;

    public boolean newPasswordEqualsConfirmPassword() {
        return this.newPassword.equals(this.confirmPassword);
    }
}
