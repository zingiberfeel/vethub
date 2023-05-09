package com.potarski.vethub.repository.mappers;

import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.animal.VetRecord;
import com.potarski.vethub.domain.user.Role;
import com.potarski.vethub.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {

    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            roles.add(Role.valueOf(resultSet.getString("user_role")));
        }
        resultSet.beforeFirst();
        List<Animal> animals = AnimalRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        List<VetRecord> vetRecords = VetRecordRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        if (resultSet.next()) {
            return User.builder()
                    .id(resultSet.getLong("user_id"))
                    .username(resultSet.getString("user_username"))
                    .password(resultSet.getString("user_password"))
                    .roles(roles)
                    .animals(animals)
                    .vetRecords(vetRecords).build();
        }
        return null;
    }

}
