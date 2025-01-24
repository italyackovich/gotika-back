package ru.gotika.gotikaback.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.auth.model.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUserIdAndIsRevokedFalse(Long id);
    Optional<Token> findByToken(String token);
}
