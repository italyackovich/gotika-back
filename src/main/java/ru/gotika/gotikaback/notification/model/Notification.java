package ru.gotika.gotikaback.notification.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.gotika.gotikaback.user.model.User;

import java.time.LocalDate;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    private String title;

    private Boolean isRead = false;

    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
