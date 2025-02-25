package ru.gotika.gotikaback.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.notification.dto.NotificationDto;
import ru.gotika.gotikaback.notification.exception.NotificationNotFoundException;
import ru.gotika.gotikaback.notification.mapper.NotificationMapper;
import ru.gotika.gotikaback.notification.model.Notification;
import ru.gotika.gotikaback.notification.repository.NotificationRepository;
import ru.gotika.gotikaback.notification.service.NotificationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;


    @Override
    public List<NotificationDto> getAllNotifications() {
        log.info("Get all notifications");
        return notificationMapper.notificationListToNotificationDtoList(notificationRepository.findAll());
    }

    @Override
    public NotificationDto getNotificationById(Long id) {
        log.info("Get notification by id: {}", id);
        return notificationMapper.notificationToNotificationDto(notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification by id: " + id + " not found")));
    }

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        log.info("Create notification: {}", notificationDto);
        Notification notification = notificationRepository.save(notificationMapper.notificationDtoToNotification(notificationDto));
        return notificationMapper.notificationToNotificationDto(notification);
    }

    @Override
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto) {
        log.info("Update notification: {}", notificationDto);
        return notificationRepository.findById(id).map(notification -> {
            Notification updatedNotification = notificationMapper.notificationDtoToNotification(notificationDto);
            updatedNotification.setId(notification.getId());
            notificationRepository.save(updatedNotification);
            return notificationMapper.notificationToNotificationDto(updatedNotification);
        }).orElseThrow(() -> new NotificationNotFoundException("Notification by id: " + id + " not found"));
    }

    @Override
    public void deleteNotification(Long id) {
        log.info("Delete notification: {}", id);
        notificationRepository.deleteById(id);
    }
}
