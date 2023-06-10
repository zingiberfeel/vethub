package com.potarski.vethub.repositories;

import com.potarski.vethub.repository.AnimalRepo;
import com.potarski.vethub.repository.DataSourceConfig;
import com.potarski.vethub.repository.impl.AnimalRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AnimalRepoImpl.class, DataSourceConfig.class})
//@Import(AnimalRepositoryImplTest.DtabaseConfiguration.class)
public class AnimalRepositoryImplTest {
    
    @Autowired
    AnimalRepo animalRepo;
    
    @Test
    public void getById(){
       var animal = animalRepo.findById(1L);
       assertNotNull(animal);
    }
    


//    @TestConfiguration
//    static class DtabaseConfiguration {
//
////        @Bean
////        public DataSource dataSource() {
////            return new Datasoruce;
////        }
//    }
}
