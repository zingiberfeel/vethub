package com.potarski.vethub.service.impl;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.exception.ResourceNotFoundException;
import com.potarski.vethub.repository.AnimalRepo;
import com.potarski.vethub.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepo animalRepo;

    @Override
    @Transactional(readOnly = true)
    public Animal getById(Long id) {
        return animalRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Animal not found")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Animal> getAllByUserId(Long id) {
        return animalRepo.findAllByUserId(id);
    }

    @Override
    public Animal update(Animal animal) {
        animalRepo.update(animal);
        return animal;
    }

    @Override
    @Transactional // Если что-то произойдет на первой операции, то create отменится и мы не создадим животное
    public Animal create(Animal animal, Long userId) {
        animalRepo.create(animal);
        animalRepo.assignToUserById(animal.getId(), userId);
        return animal;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        animalRepo.delete(id);
    }
}
