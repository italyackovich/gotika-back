package ru.gotika.gotikaback.notification.service;

import ru.gotika.gotikaback.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getAllNotifications();
    NotificationDto getNotificationById(Long id);
    NotificationDto createNotification(NotificationDto notificationDto);
    NotificationDto updateNotification(Long id, NotificationDto notificationDto);
    void deleteNotification(Long id);

}
