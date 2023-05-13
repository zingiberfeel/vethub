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
    public static List<Animal> mapRows(ResultSet resultSet) {
        List<Animal> animals = new ArrayList<>();
        while (resultSet.next()) {
            long currentAnimalId = resultSet.getLong("animal_id");
            Animal animal = null;
            for (Animal existingAnimal : animals) {
                if (existingAnimal.getId() == currentAnimalId) {
                    animal = existingAnimal;
                    break;
                }
            }
            if (animal == null) {
                animal = new Animal();
                animal.setId(currentAnimalId);
                animal.setNickname(resultSet.getString("animal_name"));
                animal.setKind(resultSet.getString("animal_kind"));
                Date date = resultSet.getDate("animal_birthdate");
                if (date != null) {
                    animal.setBirthday(date.toLocalDate());
                }
                List<VetRecord> vetRecords = new ArrayList<>();
                animal.setVetRecords(vetRecords);
                animals.add(animal);
            }
            long vetRecordId = resultSet.getLong("record_id");
            if (vetRecordId != 0) {
                VetRecord vetRecord = new VetRecord();
                vetRecord.setId(vetRecordId);
                vetRecord.setTitle(resultSet.getString("record_name"));
                vetRecord.setDescription(resultSet.getString("record_description"));
                Timestamp timestamp = resultSet.getTimestamp("record_date");
                Timestamp reminder = resultSet.getTimestamp("record_reminder");
                if (timestamp != null) {
                    vetRecord.setTimestamp(timestamp.toLocalDateTime().toLocalDate());
                }
                if (reminder != null) {
                    vetRecord.setReminder(reminder.toLocalDateTime());
                }
                animal.getVetRecords().add(vetRecord);
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
                                .distinct()
                                .collect(Collectors.toList())
                ))
                .distinct()
                .collect(Collectors.toList());
    }
}
