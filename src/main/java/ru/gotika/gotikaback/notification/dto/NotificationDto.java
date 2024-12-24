package ru.gotika.gotikaback.notification.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDto {

    private Long id;
    private String message;
    private String title;
    private Boolean isRead;
    private LocalDate createdAt;
    private Long userId;

}
