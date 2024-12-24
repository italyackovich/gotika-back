package ru.gotika.gotikaback.notification.mapper;

import org.mapstruct.Mapper;
import ru.gotika.gotikaback.notification.dto.NotificationDto;
import ru.gotika.gotikaback.notification.model.Notification;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    List<NotificationDto> notificationListToNotificationDtoList(List<Notification> notificationList);
    NotificationDto notificationToNotificationDto(Notification notification);
    Notification notificationDtoToNotification(NotificationDto notificationDto);
}
