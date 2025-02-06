package ru.gotika.gotikaback.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.common.annotation.ValidPassword;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @ValidPassword
    private String currentPassword;

    @ValidPassword
    private String newPassword;

    @ValidPassword
    private String confirmationPassword;
}
