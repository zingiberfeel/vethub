package com.potarski.vethub.repository;

import com.potarski.vethub.domain.user.Role;
import com.potarski.vethub.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username); // для проверки регистрации

    void update(User user);

    void create(User user);

    void insertUserRole(Long userId, Role role);

    boolean isAnimalOwner(Long userId, Long animalId);

    void delete(Long id);

    boolean isRecordOwner(Long userId, Long recordId);
}

