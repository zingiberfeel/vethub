package com.potarski.vethub.repository;

import com.potarski.vethub.repository.impl.AnimalRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AnimalRepoImpl.class, DataSourceConfig.class})
public class AnimalRepositoryImplTest {
    
    @Autowired
    AnimalRepo animalRepo;
    
    @Test
    public void getById(){
       var animal = animalRepo.findById(1L);
       assertNotNull(animal);
    }

}
