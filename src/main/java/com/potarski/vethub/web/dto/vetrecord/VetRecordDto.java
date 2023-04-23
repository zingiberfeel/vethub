package com.potarski.vethub.web.dto.vetrecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.web.dto.validation.OnCreate;
import com.potarski.vethub.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VetRecordDto {
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Record name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name must be less than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Length(max = 255, message = "Name must be less than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private LocalDate timestamp;

}
