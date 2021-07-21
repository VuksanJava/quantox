package com.quantox.service;

import com.quantox.api.v1.dto.UserDtoRequest;
import com.quantox.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserMapper() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public User toUser(UserDtoRequest userDtoRequest) {
        return User.builder()
                .username(userDtoRequest.getUsername())
                .firstName(userDtoRequest.getFirstName())
                .lastName(userDtoRequest.getLastName())
                .isExist(true)
                .password(bCryptPasswordEncoder.encode(userDtoRequest.getPassword()))
                .build();
    }
}