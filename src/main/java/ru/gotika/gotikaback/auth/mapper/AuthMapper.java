package ru.gotika.gotikaback.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.gotika.gotikaback.auth.dto.AuthResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    AuthResponse toAuthResponse(String accessToken, String refreshToken, Long userId);

}
