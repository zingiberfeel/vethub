package com.potarski.vethub.web.controller;

import com.potarski.vethub.domain.user.User;
import com.potarski.vethub.service.AnimalService;
import com.potarski.vethub.service.UserService;
import com.potarski.vethub.service.VetRecordService;
import com.potarski.vethub.web.mappers.AnimalMapper;
import com.potarski.vethub.web.mappers.UserMapper;
import com.potarski.vethub.web.mappers.VetRecordMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@RequiredArgsConstructor
class UserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @MockBean
    private AnimalService animalService;
    @MockBean
    private VetRecordService vetRecordService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private AnimalMapper animalMapper;
    @MockBean
    private VetRecordMapper vetRecordMapper;

    @Test
    void getById() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("John");

        when(userService.getById(userId)).thenReturn(user);

        mockMvc.perform(get("/{id}", userId).with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value(user.getUsername()));

        verify(userService, times(1)).getById(userId);
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void getAnimalsByUserId() {
    }

    @Test
    void createAnimal() {
    }

    @Test
    void getRecordsByUserId() {
    }
}