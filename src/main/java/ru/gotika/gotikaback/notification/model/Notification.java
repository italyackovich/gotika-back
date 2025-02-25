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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean isRead;

    @Column(nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
