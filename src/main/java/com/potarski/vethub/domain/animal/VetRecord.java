package com.potarski.vethub.domain.animal;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VetRecord {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime timestamp;

}
