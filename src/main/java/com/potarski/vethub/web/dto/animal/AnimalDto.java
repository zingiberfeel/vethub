package com.potarski.vethub.web.dto.animal;

import com.potarski.vethub.web.dto.validation.OnCreate;
import com.potarski.vethub.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AnimalDto {
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Animal name must not be null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 256, message = "Animal name must be less than 256 symbols")
    private String animalName;

    @NotNull(message = "Animal kind must not be null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 256, message = "Animal kind must be less than 256 symbols")
    private String animalKind;

    @NotNull(message = "Animal age must not be null", groups = {OnUpdate.class, OnCreate.class})
    @Min(0)
    private int animalAge;

    private String pictureFilePath; // фотография животного
}
