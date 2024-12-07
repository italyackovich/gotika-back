package ru.gotika.gotikaback.user.dto;

import lombok.Data;
import ru.gotika.gotikaback.user.enums.Role;

@Data
public class ChangeRoleDto {
    private Role role;
}
