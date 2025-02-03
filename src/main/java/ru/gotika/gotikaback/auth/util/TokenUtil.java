package ru.gotika.gotikaback.auth.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gotika.gotikaback.auth.model.Token;
import ru.gotika.gotikaback.auth.repository.TokenRepository;
import ru.gotika.gotikaback.user.models.User;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenUtil {

    private final StringRedisTemplate stringRedisTemplate;
    private final TokenRepository tokenRepository;

    /**
     * Revokes all valid refresh tokens associated with the specified {@link User},
     * preventing any further use of those tokens.
     *
     * @param user the user whose refresh tokens should be revoked
     */
    @Transactional
    public void revokeAllUserTokens(User user) {
        stringRedisTemplate.delete(user.getEmail());

        List<Token> validUserTokens = tokenRepository.findByUserIdAndIsRevokedFalse(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token ->
                token.setIsRevoked(true));
        tokenRepository.saveAll(validUserTokens);
        log.info("All user's tokens with id = {} is revoked", user.getId());
    }
}
