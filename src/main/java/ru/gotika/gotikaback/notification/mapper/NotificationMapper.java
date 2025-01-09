package ru.gotika.gotikaback.notification.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.gotika.gotikaback.notification.dto.NotificationDto;
import ru.gotika.gotikaback.notification.model.Notification;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    List<NotificationDto> notificationListToNotificationDtoList(List<Notification> notificationList);
    NotificationDto notificationToNotificationDto(Notification notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Notification notificationDtoToNotification(NotificationDto notificationDto);
}
