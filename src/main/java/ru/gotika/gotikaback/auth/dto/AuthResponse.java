package ru.gotika.gotikaback.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gotika.gotikaback.user.dto.UserDto;

@Data
@AllArgsConstructor
public class AuthResponse {

    private AccessRefreshCookies cookieList;
    private UserDto userDto;
}
