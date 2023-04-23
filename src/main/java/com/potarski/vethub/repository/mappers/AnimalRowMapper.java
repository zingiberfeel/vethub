package com.potarski.vethub.repository.mappers;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.animal.VetRecord;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
            Date date = resultSet.getDate("animal_birthdate");
            if (date != null){
                animal.setBirthday(date.toLocalDate());
            }
            animal.setVetRecords(vetRecords);
            return animal;
        }
        return null;
    }

    @SneakyThrows
    public static List<Animal> mapRows(ResultSet resultSet){
        List<Animal> animals = new ArrayList<>();
        while(resultSet.next()){
            Animal animal = new Animal();
            animal.setId(resultSet.getLong("animal_id"));
            animal.setNickname(resultSet.getString("animal_name"));
            animal.setKind(resultSet.getString("animal_kind"));
            Date date = resultSet.getDate("animal_birthdate");
            if (date != null){
                animal.setBirthday(date.toLocalDate());
            }
            List<VetRecord> vetRecords = new ArrayList<>(); // create an empty list of VetRecord objects
            animal.setVetRecords(vetRecords); // set the empty list to the Animal object
            animals.add(animal); // add the Animal object to the list of animals

            resultSet.previous(); // move the ResultSet cursor back one position
            while(resultSet.next()){
                long currentAnimalId = resultSet.getLong("animal_id");
                if (currentAnimalId == animal.getId()) { // if the VetRecord belongs to the current Animal
                    VetRecord vetRecord = new VetRecord();
                    vetRecord.setId(resultSet.getLong("record_id"));
                    vetRecord.setTitle(resultSet.getString("record_name"));
                    vetRecord.setDescription(resultSet.getString("record_description"));
                    Timestamp timestamp = resultSet.getTimestamp("record_date");
                    if (timestamp != null) {
                        vetRecord.setTimestamp(timestamp.toLocalDateTime().toLocalDate());
                    }
                    vetRecords.add(vetRecord); // add the VetRecord to the list of VetRecord objects for the current Animal
                } else {
                    resultSet.previous(); // move the ResultSet cursor back one position
                    break; // break out of the inner loop
                }
            }
        }
        resultSet.beforeFirst();

        return animals
                .stream()
                .map(animal -> new Animal(
                        animal.getId(),
                        animal.getNickname(),
                        animal.getKind(),
                        animal.getBirthday(),
                        animal.getVetRecords()
                                .stream()
                           //     .sorted(Comparator.comparing(VetRecord::getTimestamp))
                                .distinct()
                                .collect(Collectors.toList())))
                .distinct()
                .collect(Collectors.toList());
    }
}
