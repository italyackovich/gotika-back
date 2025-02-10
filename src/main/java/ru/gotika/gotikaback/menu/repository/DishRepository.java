package ru.gotika.gotikaback.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.menu.model.Dish;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Optional<Dish> findByName(String name);
}
