package com.potarski.vethub.domain.animal;

import com.potarski.vethub.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
public class Animal {

    private Long id;
    private String animalName;
    private String animalKind;
    private Date animalBirthDate;
    private String pictureFilePath; // фотография животного
    private List<VetRecord> vetRecords;
}
