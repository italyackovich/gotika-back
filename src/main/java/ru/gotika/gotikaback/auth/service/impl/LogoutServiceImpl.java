package ru.gotika.gotikaback.auth.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.auth.exceptions.TokenNotFoundException;
import ru.gotika.gotikaback.auth.model.Token;
import ru.gotika.gotikaback.auth.repository.TokenRepository;
import ru.gotika.gotikaback.auth.service.LogoutService;
import ru.gotika.gotikaback.auth.util.CookieUtil;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final TokenRepository tokenRepository;
    private final CookieUtil cookieUtil;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
       final String token;

       token = cookieUtil.getValueFromCookie(request, "accessTokenCookie");
       Token storedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(token));

       storedToken.setIsRevoked(true);
       tokenRepository.save(storedToken);
       cookieUtil.deleteCookie("accessTokenCookie");
       cookieUtil.deleteCookie("refreshTokenCookie");
       SecurityContextHolder.clearContext();

    }
}
