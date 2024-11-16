package ru.gotika.gotikaback.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.gotika.gotikaback.user.dto.AuthResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    AuthResponse toAuthResponse(String accessToken, String refreshToken);

}
