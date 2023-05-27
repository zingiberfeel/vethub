package com.potarski.vethub.controllers;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.user.Role;
import com.potarski.vethub.domain.user.User;
import com.potarski.vethub.repository.UserRepo;
import com.potarski.vethub.service.AnimalService;
import com.potarski.vethub.service.UserService;
import com.potarski.vethub.service.VetRecordService;
import com.potarski.vethub.web.controller.AnimalController;
import com.potarski.vethub.web.mappers.AnimalMapperImpl;
import com.potarski.vethub.web.mappers.VetRecordMapperImpl;
import com.potarski.vethub.web.security.JwtEntity;
import com.potarski.vethub.web.security.JwtEntityFactory;
import com.potarski.vethub.web.security.expression.CustomSecurityExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
@EnableMethodSecurity
@Import({AnimalMapperImpl.class, VetRecordMapperImpl.class, CustomSecurityExpression.class})
public class AnimalControllerTest {

    @Autowired
    AnimalController animalController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AnimalService animalService;

    @MockBean
    VetRecordService vetRecordService;

    @MockBean
    UserService userService;

    @MockBean
    UserRepo userRepo;

    @BeforeEach
    public void setUp() {
        var animal = new Animal(1L, "test", "test", LocalDate.now(), new ArrayList<>());

        Mockito
                .doReturn(animal)
                .when(animalService)
                .getById(1L);

        Mockito
                .doReturn(true)
                .when(userService)
                .isAnimalOwner(anyLong(), eq(1L));
    }

    @Test
    public void getById() {
        var user = User.builder().id(1L).username("test").password("test").passwordConfirmation("test").roles(Collections.singleton(Role.ROLE_USER)).build();
        JwtEntity jwtEntity = JwtEntityFactory.create(user);

        Long userId = 123L;
        Long animalId = 1L;
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                jwtEntity, "password");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(userRepo.isAnimalOwner(userId, animalId)).thenReturn(true);

        var animalDto = animalController.getById(1L);

        assertEquals(1L, animalDto.getId());
    }

    @Test
    public void getByIdMvcUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/animals/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER", username = "user")
    // @AuthenticationPrincipal
    public void getByIdMvc() throws Exception {
        mockMvc.perform(get("/api/v1/animals/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }


}
