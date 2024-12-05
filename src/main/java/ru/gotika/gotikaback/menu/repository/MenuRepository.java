package ru.gotika.gotikaback.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.menu.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
