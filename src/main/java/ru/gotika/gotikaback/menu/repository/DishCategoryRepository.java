package ru.gotika.gotikaback.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.menu.model.DishCategory;

public interface DishCategoryRepository extends JpaRepository<DishCategory, Long> {
}
