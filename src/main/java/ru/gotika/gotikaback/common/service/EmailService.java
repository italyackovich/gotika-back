package ru.gotika.gotikaback.common.service;

public interface EmailService {

    void sendEmail(String toEmail, String subject, String text);

}
