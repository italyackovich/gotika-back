package ru.gotika.gotikaback.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
