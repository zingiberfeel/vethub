package com.potarski.vethub.domain.animal;

import com.potarski.vethub.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    private Long id;
    private String nickname;
    private String kind;
    private LocalDate birthday;
    private List<VetRecord> vetRecords;
}
