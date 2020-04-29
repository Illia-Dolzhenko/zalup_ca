package com.hotmail.dolzhik.zalup_ca.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RatingDto {
    @NotNull(message = "Category cannot be empty.")
    private String category;
    @NotNull(message = "Score cannot be empty.")
    @Min(value = 1,message = "Score cannot be less then 1.")
    @Max(value = 10,message = "Score cannot be bigger than 10.")
    private Integer score;
    @NotNull(message = "Post id cannot be empty.")
    private Integer postId;
}
