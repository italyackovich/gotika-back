package ru.gotika.gotikaback.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.auth.enums.TokenType;
import ru.gotika.gotikaback.user.model.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Token cannot be blank")
    @Column(unique = true, nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @NotNull(message = "IsRevoked cannot be null")
    @Column(nullable = false)
    private Boolean isRevoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
