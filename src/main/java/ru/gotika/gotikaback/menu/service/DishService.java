package ru.gotika.gotikaback.menu.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.menu.dto.DishDto;
import ru.gotika.gotikaback.menu.enums.DishCategory;

import java.util.List;

@Service
public interface DishService {
    List<DishDto> getAllDishes();
    DishDto getDishById(long id);
    DishDto getDishByName(String name);
    List<DishDto> getDishByCategory(DishCategory category);
    DishDto createDish(DishDto dishDto);
    DishDto updateDish(Long id, DishDto dishDto);
    void deleteDish(Long id);
    DishDto changeImage(Long id, MultipartFile image);


}
