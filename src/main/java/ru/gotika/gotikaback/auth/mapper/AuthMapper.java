package ru.gotika.gotikaback.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.gotika.gotikaback.auth.dto.AuthResponse;
import ru.gotika.gotikaback.user.enums.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = { Role.class })
public interface AuthMapper {

    AuthResponse toAuthResponse(String accessToken, String refreshToken, Long userId, Role role);

}
