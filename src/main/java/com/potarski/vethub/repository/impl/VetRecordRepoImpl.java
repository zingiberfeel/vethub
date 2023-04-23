package com.potarski.vethub.repository.impl;

import com.potarski.vethub.domain.animal.VetRecord;
import com.potarski.vethub.domain.exception.ResourceMappingException;
import com.potarski.vethub.repository.DataSourceConfig;
import com.potarski.vethub.repository.VetRecordRepo;
import com.potarski.vethub.repository.mappers.VetRecordRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class VetRecordRepoImpl implements VetRecordRepo {

    private final DataSourceConfig dataSourceConfig;
    private final String FIND_BY_ID = """
            SELECT v.id              as record_id,
                   v.name            as record_name,
                   v.description     as record_description,
                   v.date            as record_date
            FROM vetrecords v
            WHERE v.id = ?
            """;

    private final String FIND_ALL_BY_ANIMAL_ID = """
            SELECT v.id              as record_id,
                   v.name            as record_name,
                   v.description     as record_description,
                   v.date            as record_date
            
            FROM vetrecords v
                     LEFT JOIN animals_vetrecords av on v.id = av.record_id
                     LEFT JOIN animals a on a.id = av.animal_id
            WHERE a.id = ?
            """;

    private final String FIND_ALL_BY_USER_ID = """
            SELECT v.id              as record_id,
                   v.name            as record_name,
                   v.description     as record_description,
                   v.date            as record_date
            FROM vetrecords v
                     LEFT JOIN animals_vetrecords av on v.id = av.record_id
                     LEFT JOIN users_animals ua on av.animal_id = ua.animal_id
                     LEFT JOIN users u on u.id = ua.user_id
            WHERE u.id = ?
            """;

    private final String ASSIGN = """
            INSERT INTO animals_vetrecords (animal_id, record_id)
            VALUES (?, ?)
            """;

    private final String UPDATE = """
            UPDATE vetrecords
            SET name = ?,
                description = ?,
                date = ?
            WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO vetrecords (name, description, date)
            VALUES (?, ?, ?)
            """;

    private final String DELETE = """
            DELETE FROM vetrecords
            WHERE id = ?
            """;

    @Override
    public Optional<VetRecord> findById(Long recordId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, recordId);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(VetRecordRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding record by id.");
        }
    }

    @Override
    public List<VetRecord> findAllByAnimalId(Long animalId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_ANIMAL_ID);
            preparedStatement.setLong(1, animalId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return VetRecordRowMapper.mapRows(resultSet);
            }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public List<VetRecord> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setLong(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return VetRecordRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void assignToAnimalById(Long recordId, Long animalId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN);
            preparedStatement.setLong(2, recordId);
            preparedStatement.setLong(1, animalId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(VetRecord vetRecord) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, vetRecord.getTitle());
            preparedStatement.setString(2, vetRecord.getDescription());
            preparedStatement.setObject(3, vetRecord.getTimestamp());
            preparedStatement.setLong(4, vetRecord.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(VetRecord vetRecord) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS); // Должны засетать айдишник в record, поэтому возвращаем ключи
            preparedStatement.setString(1, vetRecord.getTitle());
            preparedStatement.setString(2, vetRecord.getDescription());
            preparedStatement.setObject(3, vetRecord.getTimestamp());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                vetRecord.setId(rs.getLong(1));
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
            throw new ResourceMappingException("Error deleting record by id");
        }
    }
}
