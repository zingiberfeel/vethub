package com.potarski.vethub.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.potarski.vethub.web.dto.validation.OnCreate;
import com.potarski.vethub.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class UserDto {

    @NotNull(message = "Id must be not null", groups = OnUpdate.class) // мы не знаем, какой айди будет у пользователя при создании, но при обновлении мы точно не хотим зарядить null
    private Long id;

    @NotNull(message = "Name must be not null", groups = {OnCreate.class, OnUpdate.class}) // если мы ставим две группы, то берем их в фигурные скобки
    @Length(max = 255, message = "Name must be less than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @NotNull(message = "Password must be not null", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // мы можем только принимать пароль в DTO, но все DTO будут отправляться без поля пароль
    private String password;

    @NotNull(message = "Password confirmation must be not null", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;
}
