package com.potarski.vethub.domain.animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VetRecord {

    private Long id;

    private String title;

    private String description;

    private LocalDate timestamp;

}
