package com.potarski.vethub.repository.impl;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.exception.ResourceMappingException;
import com.potarski.vethub.repository.AnimalRepo;
import com.potarski.vethub.repository.DataSourceConfig;
import com.potarski.vethub.repository.mappers.AnimalRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                   v.description     as record_description,
                   v.date            as record_date,
                   v.reminder        as record_reminder
                   
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
                   v.description     as record_description,
                   v.date            as record_date,
                   v.reminder        as record_reminder
                   
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
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setLong(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.ofNullable(AnimalRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException(throwables.getMessage());
        }
    }

    @Override
    public List<Animal> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER_ID, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setLong(1, userId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                return AnimalRowMapper.mapRows(rs);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException(throwables.getMessage());
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
            throw new ResourceMappingException("Error while assigning animal by id");
        }
    }

    @Override
    public void update(Animal animal) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, animal.getNickname());
            preparedStatement.setString(2, animal.getKind());
            preparedStatement.setDate(3, Date.valueOf(animal.getBirthday()));
            preparedStatement.setLong(4, animal.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException(throwables.getMessage());
        }
    }

    @Override
    public void create(Animal animal) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS); // Должны засетать айдишник в animal, поэтому возвращаем ключи
            preparedStatement.setString(1, animal.getNickname());
            preparedStatement.setString(2, animal.getKind());
            preparedStatement.setDate(3, Date.valueOf(animal.getBirthday()));
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                animal.setId(rs.getLong(1));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException(throwables.getMessage());
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
