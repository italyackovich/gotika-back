package ru.gotika.gotikaback.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.gotika.gotikaback.common.annotation.ValidPhoneNumber;

@Data
public class ChangeUserCredentials {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    private String lastName;

    @ValidPhoneNumber
    private String phoneNumber;
}
