package com.potarski.vethub.repository.impl;

import com.potarski.vethub.domain.exception.ResourceMappingException;
import com.potarski.vethub.domain.user.Role;
import com.potarski.vethub.domain.user.User;
import com.potarski.vethub.repository.DataSourceConfig;
import com.potarski.vethub.repository.UserRepo;
import com.potarski.vethub.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT u.id              as user_id,
                   u.username        as user_username,
                   u.password        as user_password,
                   r.role            as user_role,
                   a.id              as animal_id,
                   a.name            as animal_name,
                   a.kind            as animal_kind,
                   a.birthdate       as animal_birthdate,
                   v.id              as record_id,
                   v.name            as record_name,
                   v.description     as record_description,
                   v.date            as record_date
                        
            FROM users u
                        
                     LEFT JOIN users_animals ua on u.id = ua.user_id
                     LEFT JOIN roles r on u.id = r.user_id
                     LEFT JOIN animals_vetrecords av on ua.animal_id = av.animal_id
                     LEFT JOIN animals a on a.id = av.animal_id
                     LEFT JOIN vetrecords v on v.id = av.record_id
                        
            WHERE u.id = ?
            """;

    private final String FIND_BY_USERNAME = """
            SELECT u.id              as user_id,
                   u.username        as user_username,
                   u.password        as user_password,
                   r.role            as user_role,
                   a.id              as animal_id,
                   a.name            as animal_name,
                   a.kind            as animal_kind,
                   a.birthdate       as animal_birthdate,
                   v.id              as record_id,
                   v.name            as record_name,
                   v.description     as record_description,
                   v.date            as record_date
                        
            FROM users u
                        
                     LEFT JOIN users_animals ua on u.id = ua.user_id
                     LEFT JOIN roles r on u.id = r.user_id
                     LEFT JOIN animals_vetrecords av on ua.animal_id = av.animal_id
                     LEFT JOIN animals a on a.id = av.animal_id
                     LEFT JOIN vetrecords v on v.id = av.record_id
                        
            WHERE u.username = ?
            """;


    private final String UPDATE = """
            UPDATE users
            SET username = ?,
                password = ?
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO users (username, password)
                            VALUES (?, ?)""";

    private final String INSERT_USER_ROLE = """
            INSERT INTO roles (user_id, role)
            VALUES (?, ?)""";

    private final String IS_ANIMAL_OWNER = """
            SELECT exists(
                           SELECT 1
                           FROM users_animals
                           WHERE user_id = ?
                             AND animal_id = ?
                       )""";

    private final String IS_RECORD_OWNER = """
            SELECT exists(
                           SELECT 1
                           FROM users_animals
                           LEFT JOIN animals_vetrecords av on users_animals.animal_id = av.animal_id
                           WHERE user_id = ?
                             AND record_id = ?
                       )""";

    private final String DELETE = """
            DELETE FROM users
            WHERE id = ?""";

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by id.");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by username.");
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while updating user.");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                user.setId(rs.getLong(1));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while creating user.");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while inserting user role.");
        }
    }

    @Override
    public boolean isAnimalOwner(Long userId, Long animalId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_ANIMAL_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, animalId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while checking if user is animal owner..");
        }
    }

    @Override
    public boolean isRecordOwner(Long userId, Long recordId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_RECORD_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, recordId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while checking if user is record owner..");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while deleting user.");
        }
    }


}
