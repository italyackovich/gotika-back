package ru.gotika.gotikaback.auth.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.auth.exceptions.TokenNotFoundException;
import ru.gotika.gotikaback.auth.model.Token;
import ru.gotika.gotikaback.auth.repository.TokenRepository;
import ru.gotika.gotikaback.auth.service.AuthService;
import ru.gotika.gotikaback.auth.service.JwtService;
import ru.gotika.gotikaback.auth.service.LogoutService;
import ru.gotika.gotikaback.auth.util.CookieUtil;
import ru.gotika.gotikaback.user.exceptions.UserNotFoundException;
import ru.gotika.gotikaback.user.models.User;
import ru.gotika.gotikaback.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutServiceImpl implements LogoutService {

    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;
    private final JwtService jwtService;
    private final AuthService authService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
       final String token;

       token = cookieUtil.getValueFromCookie(request, "accessTokenCookie");
       String userEmail = jwtService.extractUsername(token);
       User user = userRepository.findByEmail(userEmail).orElseThrow(() -> {
           log.warn("User with email {} not found", userEmail);
           return new UserNotFoundException(userEmail);
       });

       authService.revokeAllUserTokens(user);

       cookieUtil.deleteCookie("accessTokenCookie");
       cookieUtil.deleteCookie("refreshTokenCookie");
       SecurityContextHolder.clearContext();

       log.info("Logout of user with id {} is success", user.getId());

    }
}
