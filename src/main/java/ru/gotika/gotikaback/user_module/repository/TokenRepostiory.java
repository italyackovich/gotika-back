package ru.gotika.gotikaback.user_module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gotika.gotikaback.user_module.models.Token;

@Repository
public interface TokenRepostiory extends JpaRepository<Token, Long> {
}
