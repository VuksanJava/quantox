package com.quantox.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private String username;



    public JwtResponseDto(String jwt, String username) {
        this.token = jwt;
        this.username = username;

    }
}