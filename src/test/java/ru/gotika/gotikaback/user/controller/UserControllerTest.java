package ru.gotika.gotikaback.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.service.UserService;

import java.util.List;

@WebMvcTest(
        controllers = UserController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class
        }
)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private StringRedisTemplate stringRedisTemplate;
    @MockBean
    private JwtService jwtService;

    @Test
    void getAllUsers_ShouldReturnListOfUserDto() throws Exception {
        UserDto user1 = new UserDto();
        user1.setId(1L);
        user1.setFirstName("John");

        UserDto user2 = new UserDto();
        user2.setId(2L);
        user2.setFirstName("Jane");

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }
}
