package com.potarski.vethub.web.mappers;

import com.potarski.vethub.domain.user.User;
import com.potarski.vethub.web.dto.user.UserDto;
import org.mapstruct.Mapper;


// Нам не нужно придумывать свою логику,
// если в DTO поля называются как в Entity,
// то MapStruct сам все смэпит

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
