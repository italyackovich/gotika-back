package ru.gotika.gotikaback.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.notification.dto.NotificationDto;
import ru.gotika.gotikaback.notification.mapper.NotificationMapper;
import ru.gotika.gotikaback.notification.model.Notification;
import ru.gotika.gotikaback.notification.repository.NotificationRepository;
import ru.gotika.gotikaback.notification.service.NotificationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;


    @Override
    public List<NotificationDto> getAllNotifications() {
        return notificationMapper.notificationListToNotificationDtoList(notificationRepository.findAll());
    }

    @Override
    public NotificationDto getNotificationById(Long id) {
        return notificationMapper.notificationToNotificationDto(notificationRepository.findById(id).orElse(null));
    }

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = notificationRepository.save(notificationMapper.notificationDtoToNotification(notificationDto));
        return notificationMapper.notificationToNotificationDto(notification);
    }

    @Override
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto) {
        return notificationRepository.findById(id).map(notification -> {
            Notification updatedNotification = notificationMapper.notificationDtoToNotification(notificationDto);
            updatedNotification.setId(notification.getId());
            notificationRepository.save(updatedNotification);
            return notificationMapper.notificationToNotificationDto(updatedNotification);
        }).orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
