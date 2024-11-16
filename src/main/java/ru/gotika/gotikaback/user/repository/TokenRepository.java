package ru.gotika.gotikaback.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gotika.gotikaback.user.models.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and t.revoked = false\s
      """)
    List<Token> findAllValidTokenByUser(Long id);
    Optional<Token> findByToken(String token);
}
