package ru.gotika.gotikaback.menu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.menu.dto.DishChangeRequest;
import ru.gotika.gotikaback.menu.dto.DishDto;
import ru.gotika.gotikaback.menu.exception.DishNotFoundException;
import ru.gotika.gotikaback.menu.mapper.DishMapper;

import ru.gotika.gotikaback.menu.model.Dish;
import ru.gotika.gotikaback.menu.repository.DishRepository;
import ru.gotika.gotikaback.menu.service.DishService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<DishDto> getAllDishes() {
        log.info("Get all dishes");
        return dishMapper.dishListToDishDtoList(dishRepository.findAll());
    }

    @Override
    public DishDto getDishById(long id) {
        log.info("Get dish by id: {}", id);
        return dishMapper.dishToDishDto(dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found")));
    }

    @Override
    public DishDto createDish(DishDto dishDto) {
        Dish dish = dishRepository.save(dishMapper.dishDtoToDish(dishDto));
        log.info("Create dish: {}", dish);
        return dishMapper.dishToDishDto(dish);
    }

    @Override
    public DishDto changeDishData(Long id, DishChangeRequest dishChangeRequest) {
        return dishRepository.findById(id).map(dish -> {
            dish.setPrice(dishChangeRequest.getPrice());
            dish.setName(dishChangeRequest.getName());
            dish.setDescription(dishChangeRequest.getDescription());
            dishRepository.save(dish);
            log.info("Change dish data: {}", dish);
            return dishMapper.dishToDishDto(dish);
        }).orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found"));
    }

    @Override
    public DishDto updateDish(Long id, DishDto dishDto) {
        return dishRepository.findById(id).map(dish -> {
            Dish updatedDish = dishMapper.dishDtoToDish(dishDto);
            updatedDish.setId(dish.getId());
            dishRepository.save(updatedDish);
            log.info("Update dish: {}", updatedDish);
            return dishMapper.dishToDishDto(updatedDish);
        }).orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found"));
    }

    @Override
    public DishDto changeImage(Long id, MultipartFile image) {
        return dishRepository.findById(id).map(dish -> {
            dish.setImageUrl(cloudinaryService.uploadFile(image));
            dishRepository.save(dish);
            log.info("Change dish image: {}", dish);
            return dishMapper.dishToDishDto(dish);
        }).orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found"));
    }

    @Override
    public void deleteDish(Long id) {
        log.info("Delete dish with id: {} not found", id);
        dishRepository.deleteById(id);
    }
}
