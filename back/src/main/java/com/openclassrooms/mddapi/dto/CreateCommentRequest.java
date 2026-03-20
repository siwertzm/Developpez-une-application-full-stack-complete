package com.openclassrooms.mddapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateCommentRequest {

    @NotBlank
    private String content;
}