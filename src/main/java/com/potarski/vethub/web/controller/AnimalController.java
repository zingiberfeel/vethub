package com.potarski.vethub.web.controller;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.animal.VetRecord;
import com.potarski.vethub.service.AnimalService;
import com.potarski.vethub.service.VetRecordService;
import com.potarski.vethub.web.dto.animal.AnimalDto;
import com.potarski.vethub.web.dto.user.UserDto;
import com.potarski.vethub.web.dto.validation.OnCreate;
import com.potarski.vethub.web.dto.validation.OnUpdate;
import com.potarski.vethub.web.dto.vetrecord.VetRecordDto;
import com.potarski.vethub.web.mappers.AnimalMapper;
import com.potarski.vethub.web.mappers.VetRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/animals")
@RequiredArgsConstructor
@Validated
public class AnimalController {
    private final AnimalService animalService;
    private final VetRecordService vetRecordService;
    private final AnimalMapper animalMapper;
    private final VetRecordMapper vetRecordMapper;



    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessAnimal(#id)")
    public AnimalDto getById(@PathVariable Long id){
        Animal animal = animalService.getById(id);
        return animalMapper.toDto(animal);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessAnimal(#id)")
    public void deleteById(@PathVariable Long id){
        animalService.delete(id);
    }

    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessAnimal(#animalDto.id)")
    public AnimalDto update(@Validated(OnUpdate.class) @RequestBody AnimalDto animalDto){
        Animal animal = animalMapper.toEntity(animalDto);
        Animal updatedAnimal = animalService.update(animal);
        return animalMapper.toDto(updatedAnimal);
    }

    @PostMapping("/{id}/records")
    @PreAuthorize("@customSecurityExpression.canAccessAnimal(#id)")
    public VetRecordDto createRecord(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody VetRecordDto vetRecordDto){
        VetRecord vetRecord = vetRecordMapper.toEntity(vetRecordDto);
        VetRecord createdVetRecord = vetRecordService.create(vetRecord, id);
        return vetRecordMapper.toDto(createdVetRecord);
    }
}
