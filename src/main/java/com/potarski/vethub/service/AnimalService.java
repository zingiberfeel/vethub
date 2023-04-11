package com.potarski.vethub.service;

import com.potarski.vethub.domain.animal.Animal;

import java.util.List;

public interface AnimalService {

    Animal getById(Long id);

    List<Animal> getAllByUserId(Long id);

    Animal update(Animal animal);

    Animal create(Animal animal, Long id);

    void delete(Long id);

}
