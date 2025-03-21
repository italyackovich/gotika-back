package ru.gotika.gotikaback.auth.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.gotika.gotikaback.auth.exception.MissingCookieException;
import ru.gotika.gotikaback.auth.service.JwtService;
import ru.gotika.gotikaback.auth.util.CookieUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (
                request.getServletPath().contains("/api/v1/auth") ||
                request.getServletPath().contains("/v3") ||
                request.getServletPath().contains("/swagger-ui")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt;

        try {
            jwt = CookieUtil.getValueFromCookie(request, "accessTokenCookie");
        } catch (MissingCookieException e) {
            log.warn("Cookie is missing");
            filterChain.doFilter(request, response);
            return;
        }

        final String userEmail;

        try {
            userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                boolean isAccessTokenValid = Boolean.TRUE.equals(stringRedisTemplate.hasKey(userEmail));


                if (jwtService.isTokenValid(jwt, userDetails) && isAccessTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e) {
            log.warn("User has expired token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token is expired\"}");
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);

    }
}


