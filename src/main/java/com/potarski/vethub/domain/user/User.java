package com.potarski.vethub.domain.user;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.animal.VetRecord;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class User {

    private Long id;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Animal> animals;
    private List<VetRecord> vetRecords;
}
