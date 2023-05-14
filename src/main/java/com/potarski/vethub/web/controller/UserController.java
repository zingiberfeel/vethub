package com.potarski.vethub.web.controller;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.animal.VetRecord;
import com.potarski.vethub.domain.user.User;
import com.potarski.vethub.service.AnimalService;
import com.potarski.vethub.service.UserService;
import com.potarski.vethub.service.VetRecordService;
import com.potarski.vethub.web.dto.animal.AnimalDto;
import com.potarski.vethub.web.dto.user.UserDto;
import com.potarski.vethub.web.dto.validation.OnCreate;
import com.potarski.vethub.web.dto.validation.OnUpdate;
import com.potarski.vethub.web.dto.vetrecord.VetRecordDto;
import com.potarski.vethub.web.mappers.AnimalMapper;
import com.potarski.vethub.web.mappers.UserMapper;
import com.potarski.vethub.web.mappers.VetRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor // создает конструктор final-полей
@Validated
public class UserController {

    private final UserService userService;
    private final AnimalService animalService;

    private final VetRecordService vetRecordService;
    private final UserMapper userMapper;
    private final AnimalMapper animalMapper;

    private final VetRecordMapper vetRecordMapper;

    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessData(#id)")
    public UserDto getById(@PathVariable Long id){
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessData(#id)")
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessData(#userDto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto){
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}/animals")
    @PreAuthorize("@customSecurityExpression.canAccessData(#id)")
    public List<AnimalDto> getAnimalsByUserId(@PathVariable Long id){
        List<Animal> animals = animalService.getAllByUserId(id);
        return animalMapper.toDto(animals);
    }

    @PostMapping("{id}/animals")
    @PreAuthorize("@customSecurityExpression.canAccessData(#id)")
    public AnimalDto createAnimal(@PathVariable Long id,
                                  @Validated(OnCreate.class) @RequestBody AnimalDto animalDto) {
        Animal animal = animalMapper.toEntity(animalDto);
        Animal createdAnimal = animalService.create(animal, id);
        return animalMapper.toDto(createdAnimal);
    }

    @GetMapping("/{id}/records")
    @PreAuthorize("@customSecurityExpression.canAccessData(#id)")
    public List<VetRecordDto> getRecordsByUserId(@PathVariable Long id){
        List<VetRecord> records = vetRecordService.getAllByUserId(id);
        return vetRecordMapper.toDto(records);
    }

}
