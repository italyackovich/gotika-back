package ru.gotika.gotikaback.common.service;

public interface CodeStorageService {

    void saveCode(String email, String code);
    String getCode(String email);
    void deleteCode(String email);
}
