package com.potarski.vethub.domain.animal;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VetRecord {

    private Long id;

    private String recordName;

    private String recordDescription;

    private Animal animal;

    private LocalDateTime timestamp;

}
