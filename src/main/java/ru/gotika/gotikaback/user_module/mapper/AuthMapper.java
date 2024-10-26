package ru.gotika.gotikaback.user_module.mapper;

import org.mapstruct.Mapper;
import ru.gotika.gotikaback.user_module.dto.AuthResponse;

@Mapper
public interface AuthMapper {

    AuthResponse toAuthResponse(String accessToken, String refreshToken);

}
