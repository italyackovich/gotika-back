package ru.gotika.gotikaback.user.dto;

import lombok.Data;

@Data
public class ChangeUserCredentials {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
