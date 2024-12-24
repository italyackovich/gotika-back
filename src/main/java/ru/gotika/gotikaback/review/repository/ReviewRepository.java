package ru.gotika.gotikaback.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.review.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
