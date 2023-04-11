package com.potarski.vethub.repository.impl;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.exception.ResourceMappingException;
import com.potarski.vethub.repository.AnimalRepo;
import com.potarski.vethub.repository.DataSourceConfig;
import com.potarski.vethub.repository.mappers.AnimalRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnimalRepoImpl implements AnimalRepo {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT a.id              as animal_id,
                   a.name            as animal_name,
                   a.kind            as animal_kind,
                   a.birthdate       as animal_birthdate,
                   v.id              as record_id,
                   v.name            as record_name,
                   v.description     as record_description
            FROM animals a
                     LEFT JOIN animals_vetrecords av on a.id = av.animal_id
                     LEFT JOIN vetrecords v on av.record_id = v.id
            WHERE a.id = ?""";

    private final String FIND_ALL_BY_USER_ID = """
            SELECT a.id              as animal_id,
                   a.name            as animal_name,
                   a.kind            as animal_kind,
                   a.birthdate       as animal_birthdate,
                   v.id              as record_id,
                   v.name            as record_name,
                   v.description     as record_description
            FROM animals a
                     JOIN users_animals ua on a.id = ua.animal_id
                     LEFT JOIN animals_vetrecords av on a.id = av.animal_id
                     LEFT JOIN vetrecords v on av.record_id = v.id
            WHERE ua.user_id = ?""";

    private final String ASSIGN = """
            INSERT INTO users_animals (animal_id, user_id)
            VALUES (?, ?)""";

    private final String UPDATE = """
            UPDATE animals
            SET name = ?,
                kind = ?,
                birthdate = ?
            WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO animals (name, kind, birthdate)
            VALUES (?, ?, ?)""";

    private final String DELETE = """
            DELETE FROM animals
            WHERE id = ?""";

    @Override
    public Optional<Animal> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.ofNullable(AnimalRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding animal by id");
        }
    }

    @Override
    public List<Animal> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                return AnimalRowMapper.mapRows(rs);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding all animals by user id");
        }
    }

    @Override
    public void assignToUserById(Long animalId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN);
            preparedStatement.setLong(1, animalId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding animal by id");
        }
    }

    @Override
    public void update(Animal animal) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, animal.getAnimalName());
            preparedStatement.setString(2, animal.getAnimalKind());
            preparedStatement.setDate(3, animal.getAnimalBirthDate());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while updating animal");
        }
    }

    @Override
    public void create(Animal animal) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS); // Должны засетать айдишник в animal, поэтому возвращаем ключи
            preparedStatement.setString(1, animal.getAnimalName());
            preparedStatement.setString(2, animal.getAnimalKind());
            preparedStatement.setDate(3, animal.getAnimalBirthDate());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                animal.setId(rs.getLong(1));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while creating animal");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error deleting animal by id");
        }
    }
}
