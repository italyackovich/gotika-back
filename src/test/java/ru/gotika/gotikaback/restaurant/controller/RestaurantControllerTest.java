package ru.gotika.gotikaback.restaurant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.gotika.gotikaback.auth.service.JwtService;
import ru.gotika.gotikaback.restaurant.dto.ResponseRestaurantDto;
import ru.gotika.gotikaback.restaurant.service.RestaurantService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = RestaurantController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class
        }
)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private StringRedisTemplate stringRedisTemplate;
    @MockBean
    private JwtService jwtService;

    @Test
    void getRestaurants_ShouldReturnListOfResponseRestaurantDto() throws Exception {
        ResponseRestaurantDto restaurant1 = new ResponseRestaurantDto();
        restaurant1.setId(1L);
        restaurant1.setName("restaurantName1");

        ResponseRestaurantDto restaurant2 = new ResponseRestaurantDto();
        restaurant2.setId(2L);
        restaurant2.setName("restaurantName2");

        when(restaurantService.getRestaurants()).thenReturn(List.of(restaurant1, restaurant2));

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("restaurantName1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("restaurantName2"));
    }
}
