package ru.gotika.gotikaback.menu.service;

import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.menu.dto.DishDto;

import java.util.List;

@Service
public interface DishService {
    List<DishDto> getDishes();
    DishDto getDishById(long id);
    DishDto getDishByName(String name);
    DishDto getDishByCategory(String category);


}
