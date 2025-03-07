package ru.gotika.gotikaback.user.controller;

import static org.mockito.Mockito.*;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.gotika.gotikaback.auth.service.JwtService;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.enums.Role;
import ru.gotika.gotikaback.user.exception.UserNotFoundException;
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

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void getUser_ShouldReturnUserDto_WhenUserExists() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setFirstName("John");

        when(userService.getUser(userId)).thenReturn(userDto);

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getUser_ShouldThrowException_WhenUserDoesNotExist() throws Exception {
        Long userId = 999L;

        when(userService.getUser(userId))
                .thenThrow(new UserNotFoundException("User with id " + userId + " not found"));

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_ShouldReturnCreatedUserDto() throws Exception {
        String requestBody = """
                {
                  "firstName": "John",
                  "lastName": "Doe",
                  "email": "john.doe@example.com",
                  "password": "password123"
                }
                """;
        UserDto createdUser = new UserDto();
        createdUser.setId(1L);
        createdUser.setFirstName("John");
        createdUser.setLastName("Doe");
        createdUser.setEmail("john.doe@example.com");
        createdUser.setPassword("password123");

        when(userService.createUser(Mockito.any(UserDto.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/v1/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.password").value("password123"));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserDto() throws Exception {
        Long userId = 1L;
        String requestBody = """
                {
                  "firstName": "UpdatedFirst",
                  "lastName": "UpdatedLast",
                  "email": "updated@example.com",
                  "password": "newPassword"
                }
                """;

        UserDto updatedUser = new UserDto();
        updatedUser.setId(userId);
        updatedUser.setFirstName("UpdatedFirst");
        updatedUser.setLastName("UpdatedLast");
        updatedUser.setEmail("updated@example.com");

        when(userService.updateUser(Mockito.eq(userId), Mockito.any()))
                .thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/{id}/update", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("UpdatedFirst"))
                .andExpect(jsonPath("$.lastName").value("UpdatedLast"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void changeAddress_ShouldReturnUpdatedUserDto() throws Exception {
        Long userId = 1L;
        String requestBody =
                """
                {
                  "address": "123 New Street"
                }
                """;

        UserDto updatedUser = new UserDto();
        updatedUser.setId(userId);
        updatedUser.setAddress("123 New Street");

        when(userService.changeAddress(Mockito.eq(userId), Mockito.any()))
                .thenReturn(updatedUser);

        mockMvc.perform(patch("/api/v1/users/{id}/ch-address", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.address").value("123 New Street"));
    }

    @Test
    void changeCred_ShouldReturnUpdatedUserDto() throws Exception {
        Long userId = 1L;
        String requestBody =
                """
                {
                  "firstName":"NewFirst",
                  "lastName":"NewLast",
                  "phoneNumber":"+79243332999"
                }
                """;

        UserDto updatedUser = new UserDto();
        updatedUser.setId(userId);
        updatedUser.setFirstName("NewFirst");
        updatedUser.setLastName("NewLast");
        updatedUser.setPhoneNumber("+79243332999");

        when(userService.changeCred(Mockito.eq(userId), Mockito.any()))
                .thenReturn(updatedUser);

        mockMvc.perform(patch("/api/v1/users/{id}/ch-cred", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("NewFirst"))
                .andExpect(jsonPath("$.lastName").value("NewLast"))
                .andExpect(jsonPath("$.phoneNumber").value("+79243332999"));
    }

    @Test
    void changeRole_ShouldReturnUpdatedUserDto() throws Exception {
        Long userId = 1L;
        String requestBody =
                """
                {
                  "role":"ROLE_ADMIN"
                }
                """;

        UserDto updatedUser = new UserDto();
        updatedUser.setId(userId);
        updatedUser.setRole(Role.ROLE_ADMIN);

        when(userService.changeRole(Mockito.eq(userId), Mockito.any()))
                .thenReturn(updatedUser);

        mockMvc.perform(patch("/api/v1/users/{id}/ch-role", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }

    @Test
    void changeImage_ShouldReturnUpdatedUserDto() throws Exception {
        Long userId = 1L;
        // Создаем моковый multipart файл
        byte[] fileContent = "dummy image content".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, fileContent);

        UserDto updatedUser = new UserDto();
        updatedUser.setId(userId);
        updatedUser.setImageUrl("http://cloudinary.com/newImage.jpg");

        when(userService.changeImage(Mockito.eq(userId), Mockito.any()))
                .thenReturn(updatedUser);

        mockMvc.perform(multipart("/api/v1/users/{id}/ch-image", userId)
                        .file(file)
                        // Из-за того, что multipart по умолчанию использует POST, а у нас PATCH, используем "with" для смены метода
                        .with(request -> { request.setMethod("PATCH"); return request; }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.imageUrl").value("http://cloudinary.com/newImage.jpg"));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/v1/users/delete/{id}", userId))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(userId);
    }

}
