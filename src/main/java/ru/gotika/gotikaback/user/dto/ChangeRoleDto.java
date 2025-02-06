package ru.gotika.gotikaback.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.gotika.gotikaback.user.enums.Role;

@Data
public class ChangeRoleDto {

    @NotNull(message = "Role cannot be null")
    private Role role;
}
