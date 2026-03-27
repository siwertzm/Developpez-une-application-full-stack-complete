package com.openclassrooms.mddapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}