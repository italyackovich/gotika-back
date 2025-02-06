package ru.gotika.gotikaback.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeAddress {
    @NotBlank(message = "Address cannot be blank")
    private String address;
}
