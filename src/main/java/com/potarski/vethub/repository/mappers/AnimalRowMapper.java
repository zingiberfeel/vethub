package com.potarski.vethub.repository.mappers;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.animal.VetRecord;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalRowMapper {

    @SneakyThrows
    public static Animal mapRow(ResultSet resultSet){
        List<VetRecord> vetRecords = VetRecordRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        if(resultSet.next()){
            Animal animal = new Animal();
            animal.setId(resultSet.getLong("animal_id"));
            animal.setNickname(resultSet.getString("animal_name"));
            animal.setKind(resultSet.getString("animal_kind"));
            animal.setBirthday(resultSet.getDate("animal_birthdate"));
            animal.setVetRecords(vetRecords);
            return animal;
        }
        return null;
    }

    @SneakyThrows
    public static List<Animal> mapRows(ResultSet resultSet){
        List<Animal> animals = new ArrayList<>();
        List<VetRecord> vetRecords = VetRecordRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        while(resultSet.next()){
            Animal animal = new Animal();
            animal.setId(resultSet.getLong("animal_id"));
            animal.setNickname(resultSet.getString("animal_name"));
            animal.setKind(resultSet.getString("animal_kind"));
            animal.setBirthday(resultSet.getDate("animal_birthdate"));
            animal.setVetRecords(vetRecords);
            animals.add(animal);
        }
        List<Animal> uniqueAnimals = animals.stream()
                .map(animal -> new Animal(
                        animal.getId(),
                        animal.getNickname(),
                        animal.getKind(),
                        animal.getBirthday(),
                        animal.getVetRecords().stream()
                                .sorted(Comparator.comparing(VetRecord::getTimestamp))
                                .distinct()
                                .collect(Collectors.toList())))
                .distinct()
                .collect(Collectors.toList());

        return uniqueAnimals;
    }
}
