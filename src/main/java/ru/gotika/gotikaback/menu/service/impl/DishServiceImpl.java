package ru.gotika.gotikaback.menu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.menu.dto.DishChangeRequest;
import ru.gotika.gotikaback.menu.dto.DishDto;
import ru.gotika.gotikaback.menu.enums.DishCategory;
import ru.gotika.gotikaback.menu.mapper.DishMapper;

import ru.gotika.gotikaback.menu.model.Dish;
import ru.gotika.gotikaback.menu.repository.DishRepository;
import ru.gotika.gotikaback.menu.service.DishService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<DishDto> getAllDishes() {
        return dishMapper.dishListToDishDtoList(dishRepository.findAll());
    }

    @Override
    public DishDto getDishById(long id) {
        return dishMapper.dishToDishDto(dishRepository.findById(id).orElse(null));
    }

    @Override
    public DishDto getDishByName(String name) {
        return dishMapper.dishToDishDto(dishRepository.findByName(name).orElse(null));
    }

    @Override
    public List<DishDto> getDishByCategory(DishCategory category) {
        return dishMapper.dishListToDishDtoList(dishRepository.findByCategory(category));
    }

    @Override
    public DishDto patchDish(Long id, DishChangeRequest dishChangeRequest) {
        return dishRepository.findById(id).map(dish -> {
            dish.setPrice(dishChangeRequest.getPrice());
            dish.setName(dishChangeRequest.getName());
            dish.setDescription(dishChangeRequest.getDescription());
            dishRepository.save(dish);
            return dishMapper.dishToDishDto(dish);
        }).orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    @Override
    public DishDto createDish(DishDto dishDto) {
        Dish dish = dishRepository.save(dishMapper.dishDtoToDish(dishDto));
        return dishMapper.dishToDishDto(dish);
    }

    @Override
    public DishDto updateDish(Long id, DishDto dishDto) {
        return dishRepository.findById(id).map(dish -> {
            Dish updatedDish = dishMapper.dishDtoToDish(dishDto);
            updatedDish.setId(dish.getId());
            dishRepository.save(updatedDish);
            return dishMapper.dishToDishDto(updatedDish);
        }).orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    @Override
    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public DishDto changeImage(Long id, MultipartFile image) {
        return dishRepository.findById(id).map(dish -> {
            dish.setImageUrl(cloudinaryService.uploadFile(image));
            dishRepository.save(dish);
            return dishMapper.dishToDishDto(dish);
        }).orElse(null);
    }
}
