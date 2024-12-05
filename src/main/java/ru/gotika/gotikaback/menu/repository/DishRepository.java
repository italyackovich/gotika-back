package ru.gotika.gotikaback.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.menu.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
