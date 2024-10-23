package ru.gotika.gotikaback.userModule.entity.token;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Token {

    @Id
    @GeneratedValue
    public Long id;
    public String token;

    public TokenType tokenType;

}
