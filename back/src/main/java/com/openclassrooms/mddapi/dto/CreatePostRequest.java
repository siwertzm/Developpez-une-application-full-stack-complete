package com.openclassrooms.mddapi.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.util.UUID;

@Getter
@Setter
public class CreatePostRequest {

    @NotNull
    private UUID topicId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}