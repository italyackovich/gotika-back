package ru.gotika.gotikaback.restaurant.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.restaurant.dto.ChangeRestaurantDescDto;
import ru.gotika.gotikaback.restaurant.dto.RequestRestaurantDto;
import ru.gotika.gotikaback.restaurant.dto.ResponseRestaurantDto;
import ru.gotika.gotikaback.restaurant.exception.RestaurantNotFoundException;
import ru.gotika.gotikaback.restaurant.mapper.RestaurantMapper;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.restaurant.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    void getRestaurants_ShouldReturnListOfResponseRestaurantDto() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L); // уникальное значение для restaurant1

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L); // уникальное значение для restaurant2

        List<Restaurant> restaurantList = List.of(restaurant1, restaurant2);

        when(restaurantRepository.findAll()).thenReturn(restaurantList);

        ResponseRestaurantDto dto1 = new ResponseRestaurantDto();
        dto1.setId(1L); // установите идентификатор или другое поле
        ResponseRestaurantDto dto2 = new ResponseRestaurantDto();
        dto2.setId(2L);

        when(restaurantMapper.restaurantToResponseRestaurantDto(restaurant1)).thenReturn(dto1);
        when(restaurantMapper.restaurantToResponseRestaurantDto(restaurant2)).thenReturn(dto2);

        List<ResponseRestaurantDto> result = restaurantService.getRestaurants();

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

        verify(restaurantRepository).findAll();
        verify(restaurantMapper).restaurantToResponseRestaurantDto(restaurant1);
        verify(restaurantMapper).restaurantToResponseRestaurantDto(restaurant2);
        verifyNoMoreInteractions(restaurantRepository, restaurantMapper);

    }


    @Test
    void getRestaurantById_ShouldReturnResponseRestaurantDto_WhenRestaurantExists() {
        Long restaurantId = 1L;
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(restaurantId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant1));

        when(restaurantMapper.restaurantToResponseRestaurantDto(restaurant1)).thenAnswer(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            ResponseRestaurantDto responseRestaurantDto = new ResponseRestaurantDto();
            responseRestaurantDto.setId(restaurant.getId());
            return responseRestaurantDto;
        });

        ResponseRestaurantDto result = restaurantService.getRestaurant(restaurantId);

        assertEquals(restaurantId, result.getId());
        verify(restaurantRepository).findById(restaurantId);
        verify(restaurantMapper).restaurantToResponseRestaurantDto(restaurant1);
        verifyNoInteractions(cloudinaryService);
    }

    @Test
    void getRestaurantById_ShouldThrowRestaurantNotFoundException_WhenRestaurantDoesNotExist() {
        Long restaurantId = 999L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.getRestaurant(restaurantId));

        verify(restaurantRepository).findById(restaurantId);
        verifyNoMoreInteractions(cloudinaryService, restaurantMapper);
    }

    @Test
    void createRestaurant_ShouldReturnResponseRestaurantDto() {
        RequestRestaurantDto requestRestaurantDto = new RequestRestaurantDto();
        requestRestaurantDto.setName("newRestaurant");

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setName("newRestaurant");
        when(restaurantMapper.requestRestaurantDtoToRestaurant(requestRestaurantDto)).thenReturn(restaurant1);

        when(restaurantRepository.save(restaurant1)).thenAnswer(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1L);
            return restaurant;
        });

        when(restaurantMapper.restaurantToResponseRestaurantDto(restaurant1)).thenAnswer(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            ResponseRestaurantDto responseRestaurantDto = new ResponseRestaurantDto();
            responseRestaurantDto.setId(restaurant.getId());
            responseRestaurantDto.setName(restaurant.getName());
            return responseRestaurantDto;
        });

        ResponseRestaurantDto result = restaurantService.createRestaurant(requestRestaurantDto);

        assertEquals("newRestaurant", result.getName());
        verify(restaurantMapper).requestRestaurantDtoToRestaurant(requestRestaurantDto);
        verify(restaurantRepository).save(restaurant1);
        verify(restaurantMapper).restaurantToResponseRestaurantDto(restaurant1);
        verifyNoInteractions(cloudinaryService);
    }

    @Test
    void changeDesc_ShouldReturnResponseRestaurantDto_WhenRestaurantExists() {
        Long restaurantId = 1L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("oldRestaurant");
        restaurant.setPhoneNumber("oldPhoneNumber");
        restaurant.setAddress("oldAddress");
        restaurant.setOpeningHours("oldOpeningHours");
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        ChangeRestaurantDescDto changeRestaurantDescDto = new ChangeRestaurantDescDto();
        changeRestaurantDescDto.setAddress("newAddress");
        changeRestaurantDescDto.setName("newName");
        changeRestaurantDescDto.setPhoneNumber("newPhoneNumber");
        changeRestaurantDescDto.setOpeningHours("newOpeningHours");

        when(restaurantRepository.save(restaurant)).thenAnswer(invocation -> {
            Restaurant restaurant1 = invocation.getArgument(0);
            restaurant1.setId(1L);
            restaurant1.setName("newRestaurant");
            restaurant1.setPhoneNumber("newPhoneNumber");
            restaurant1.setAddress("newAddress");
            restaurant1.setOpeningHours("newOpeningHours");
            return restaurant1;
        });

        when(restaurantMapper.restaurantToResponseRestaurantDto(restaurant)).thenAnswer(invocation -> {
            Restaurant restaurant1 = invocation.getArgument(0);
            ResponseRestaurantDto responseRestaurantDto = new ResponseRestaurantDto();
            responseRestaurantDto.setId(restaurant1.getId());
            responseRestaurantDto.setName(restaurant1.getName());
            responseRestaurantDto.setPhoneNumber(restaurant1.getPhoneNumber());
            responseRestaurantDto.setAddress(restaurant1.getAddress());
            responseRestaurantDto.setOpeningHours(restaurant1.getOpeningHours());
            return responseRestaurantDto;
        });

        ResponseRestaurantDto result = restaurantService.changeDesc(restaurantId, changeRestaurantDescDto);

        assertEquals("newRestaurant", result.getName());
        assertEquals("newPhoneNumber", result.getPhoneNumber());
        assertEquals("newAddress", result.getAddress());
        assertEquals("newOpeningHours", result.getOpeningHours());

        verify(restaurantRepository).findById(restaurantId);
        verify(restaurantRepository).save(restaurant);
        verify(restaurantMapper).restaurantToResponseRestaurantDto(restaurant);
        verifyNoInteractions(cloudinaryService);
    }

    @Test
    void changeDesc_ShouldThrowRestaurantNotFoundException_WhenRestaurantDoesNotExist() {
        Long restaurantId = 999L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.changeDesc(restaurantId, new ChangeRestaurantDescDto()));
        verify(restaurantRepository).findById(restaurantId);
        verifyNoMoreInteractions(restaurantMapper, cloudinaryService);
    }

    @Test
    void updateRestaurant_ShouldReturnResponseRestaurantDto_WhenRestaurantExists() {
        Long restaurantId = 1L;
        Restaurant savedRestaurant = new Restaurant();
        savedRestaurant.setId(1L);
        savedRestaurant.setName("oldRestaurant");

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(savedRestaurant));

        RequestRestaurantDto requestRestaurantDto = new RequestRestaurantDto();
        requestRestaurantDto.setId(restaurantId);
        requestRestaurantDto.setName("newRestaurant");

        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setName("newRestaurant");
        when(restaurantMapper.requestRestaurantDtoToRestaurant(requestRestaurantDto)).thenReturn(updatedRestaurant);

        when(restaurantRepository.save(updatedRestaurant)).thenAnswer(invocation -> {
            Restaurant restaurant1 = invocation.getArgument(0);
            restaurant1.setId(restaurantId);
            return restaurant1;
        });

        when(restaurantMapper.restaurantToResponseRestaurantDto(updatedRestaurant)).thenAnswer(invocation -> {
            Restaurant restaurant1 = invocation.getArgument(0);
            ResponseRestaurantDto responseRestaurantDto = new ResponseRestaurantDto();
            responseRestaurantDto.setId(restaurant1.getId());
            responseRestaurantDto.setName(restaurant1.getName());
            return responseRestaurantDto;
        });

        ResponseRestaurantDto result = restaurantService.updateRestaurant(restaurantId, requestRestaurantDto);
        assertEquals(restaurantId, result.getId());
        assertEquals("newRestaurant", result.getName());
        verify(restaurantRepository).findById(restaurantId);
        verify(restaurantMapper).requestRestaurantDtoToRestaurant(requestRestaurantDto);
        verify(restaurantRepository).save(updatedRestaurant);
        verify(restaurantMapper).restaurantToResponseRestaurantDto(updatedRestaurant);
        verifyNoInteractions(cloudinaryService);
    }

    @Test
    void updateRestaurant_ShouldThrowRestaurantNotFoundException_WhenRestaurantDoesNotExist() {
        Long restaurantId = 999L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.updateRestaurant(restaurantId, new RequestRestaurantDto()));

        verify(restaurantRepository).findById(restaurantId);
        verifyNoMoreInteractions(restaurantMapper, cloudinaryService);
    }

    @Test
    void changeImage_ShouldReturnResponseRestaurantDto_WhenRestaurantExists() {
        Long restaurantId = 1L;
        Restaurant savedRestaurant = new Restaurant();
        savedRestaurant.setId(restaurantId);
        savedRestaurant.setImageUrl("oldImageUrl");

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(savedRestaurant));

        MultipartFile file = mock(MultipartFile.class);
        String newImageUrl = "newImageUrl";

        when(cloudinaryService.uploadFile(file)).thenReturn(newImageUrl);

        savedRestaurant.setImageUrl(newImageUrl);

        when(restaurantRepository.save(savedRestaurant)).thenReturn(savedRestaurant);

        when(restaurantMapper.restaurantToResponseRestaurantDto(savedRestaurant)).thenAnswer(invocation -> {
            Restaurant restaurant1 = invocation.getArgument(0);
            ResponseRestaurantDto responseRestaurantDto = new ResponseRestaurantDto();
            responseRestaurantDto.setId(restaurant1.getId());
            responseRestaurantDto.setImageUrl(restaurant1.getImageUrl());
            return responseRestaurantDto;
        });

        ResponseRestaurantDto result = restaurantService.changeImage(restaurantId, file);

        assertEquals(restaurantId, result.getId());
        assertEquals("newImageUrl", result.getImageUrl());

        verify(restaurantRepository).findById(restaurantId);
        verify(cloudinaryService).uploadFile(file);
        verify(restaurantRepository).save(savedRestaurant);
        verify(restaurantMapper).restaurantToResponseRestaurantDto(savedRestaurant);
    }

    @Test
    void changeImage_ShouldThrowRestaurantNotFoundException_WhenRestaurantDoesNotExist() {
        Long restaurantId = 999L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.changeImage(restaurantId, mock(MultipartFile.class)));

        verify(restaurantRepository).findById(restaurantId);
        verifyNoMoreInteractions(restaurantMapper, cloudinaryService);
    }

    @Test
    void deleteRestaurant_ShouldDeleteRestaurantById() {
        Long restaurantId = 1L;

        restaurantService.deleteRestaurant(restaurantId);

        verify(restaurantRepository).deleteById(restaurantId);
    }
}
