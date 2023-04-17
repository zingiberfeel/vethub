package com.potarski.vethub.web.mappers;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.web.dto.animal.AnimalDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// Нам не нужно придумывать свою логику,
// если в DTO поля называются как в Entity,
// то MapStruct сам все смэпит

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    AnimalDto toDto(Animal animal);

    List<AnimalDto> toDto(List<Animal> animals);

    Animal toEntity(AnimalDto animalDto);
}
