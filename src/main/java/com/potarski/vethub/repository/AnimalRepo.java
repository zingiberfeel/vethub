package com.potarski.vethub.repository;

import com.potarski.vethub.domain.animal.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalRepo {

    Optional<Animal> findById(Long id);

    List<Animal> findAllByUserId(Long userId);

    void assignToUserById(Long animalId, Long userId); // При создании животного связываем его с пользователем, который его создает

    void update(Animal animal);

    void create(Animal animal);

    void delete(Long id);


}
