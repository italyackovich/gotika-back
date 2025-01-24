package ru.gotika.gotikaback.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.auth.enums.TokenType;
import ru.gotika.gotikaback.user.models.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Boolean isRevoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
