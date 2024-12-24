package ru.gotika.gotikaback.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.menu.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
