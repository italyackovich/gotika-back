package ru.gotika.gotikaback.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.user.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
