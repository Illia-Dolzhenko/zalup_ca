package com.hotmail.dolzhik.zalup_ca.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
public class CreatePostDto {
    @NotNull(message = "Image link cannot be empty.")
    private String image;
    @NotNull(message = "Destruction time cannot be empty.")
    @Digits(integer=2, fraction=0, message = "Destruction time cannot be bigger than 99.")
    private Integer timeToLive;
}
