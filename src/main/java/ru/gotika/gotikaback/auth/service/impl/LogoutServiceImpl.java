package ru.gotika.gotikaback.auth.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.auth.service.JwtService;
import ru.gotika.gotikaback.auth.service.LogoutService;
import ru.gotika.gotikaback.auth.util.CookieUtil;
import ru.gotika.gotikaback.auth.util.TokenUtil;
import ru.gotika.gotikaback.user.exception.UserNotFoundException;
import ru.gotika.gotikaback.user.model.User;
import ru.gotika.gotikaback.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutServiceImpl implements LogoutService {

    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;
    private final JwtService jwtService;
    private final TokenUtil tokenUtil;

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

       tokenUtil.revokeAllUserTokens(user);

       cookieUtil.deleteCookie("accessTokenCookie");
       cookieUtil.deleteCookie("refreshTokenCookie");
       SecurityContextHolder.clearContext();

       log.info("Logout of user with id {} is success", user.getId());

    }
}
