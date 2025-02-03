package ru.gotika.gotikaback.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gotika.gotikaback.user.dto.UserDto;

@Data
@AllArgsConstructor
public class AuthResponse {

    @NotNull(message = "CookieList is required")
    @Valid
    private AccessRefreshCookies cookieList;

    @NotNull(message = "UserDto is required")
    @Valid
    private UserDto userDto;
}
