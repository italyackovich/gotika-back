package ru.gotika.gotikaback.user_module.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.gotika.gotikaback.user_module.dto.AuthResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    AuthResponse toAuthResponse(String accessToken, String refreshToken);

}
