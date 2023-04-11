package com.potarski.vethub.web.security;

import com.potarski.vethub.domain.user.Role;
import com.potarski.vethub.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {

    // Cоздаем из юзера JwtEntity

    public static JwtEntity create(User user){
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
