package com.potarski.vethub.controllers;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.service.AnimalService;
import com.potarski.vethub.service.UserService;
import com.potarski.vethub.service.VetRecordService;
import com.potarski.vethub.web.controller.AnimalController;
import com.potarski.vethub.web.mappers.AnimalMapper;
import com.potarski.vethub.web.mappers.AnimalMapperImpl;
import com.potarski.vethub.web.mappers.VetRecordMapper;
import com.potarski.vethub.web.mappers.VetRecordMapperImpl;
import com.potarski.vethub.web.security.expression.CustomSecurityExpression;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    
    @BeforeEach
    public void setUp(){
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
    public void getById(){
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
