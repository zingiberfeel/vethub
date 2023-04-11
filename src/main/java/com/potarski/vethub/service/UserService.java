package com.potarski.vethub.service;

import com.potarski.vethub.domain.user.User;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User update(User user);

    User create(User user);

    boolean isAnimalOwner(Long userId, Long animalId);

    boolean isRecordOwner(Long userId, Long recordId);

    void delete(Long id);
}
