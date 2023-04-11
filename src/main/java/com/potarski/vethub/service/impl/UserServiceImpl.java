package com.potarski.vethub.service.impl;

import com.potarski.vethub.domain.exception.ResourceNotFoundException;
import com.potarski.vethub.domain.user.Role;
import com.potarski.vethub.domain.user.User;
import com.potarski.vethub.repository.UserRepo;
import com.potarski.vethub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public User getById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with this ID not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
    }

    @Transactional
    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.update(user);
        return user;
    }

    @Transactional
    @Override
    public User create(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.create(user);
        Set<Role> roleSet = Set.of(Role.ROLE_USER);
        userRepo.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roleSet);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isAnimalOwner(Long userId, Long animalId) {
        return userRepo.isAnimalOwner(userId, animalId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isRecordOwner(Long userId, Long recordId) {
        return userRepo.isRecordOwner(userId, recordId);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepo.delete(id);
    }
}
