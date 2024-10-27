package ru.gotika.gotikaback.user_module.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.user_module.repository.TokenRepository;
import ru.gotika.gotikaback.user_module.service.LogoutService;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
       final String authHeader = request.getHeader("Authorization");
       final String token;
       if(authHeader == null || authHeader.startsWith("Bearer ")) {
           return;
       }
       token = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(token)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
