package com.quantox.api.v1.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRequest {

    private String username;
    private String password;
    private String firstName;
    private String lastName;



}