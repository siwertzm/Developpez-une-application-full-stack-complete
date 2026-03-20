package com.openclassrooms.mddapi.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
public class UpdateProfileRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
        message = "Le mot de passe doit contenir au moins 8 caractères, une minuscule, une majuscule, un chiffre et un caractère spécial."
    )
    private String password;
}