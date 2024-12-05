package ru.gotika.gotikaback.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.restaurant.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
