package com.potarski.vethub.web.dto.animal;

import com.potarski.vethub.domain.animal.VetRecord;
import com.potarski.vethub.web.dto.validation.OnCreate;
import com.potarski.vethub.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Animal name must not be null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 256, message = "Animal name must be less than 256 symbols")
    private String nickname;

    @NotNull(message = "Animal kind must not be null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 256, message = "Animal kind must be less than 256 symbols")
    private String kind;

    @NotNull(message = "Animal age must not be null", groups = {OnUpdate.class, OnCreate.class})
    private LocalDate birthday;

    private List<VetRecord> vetRecords;
}
