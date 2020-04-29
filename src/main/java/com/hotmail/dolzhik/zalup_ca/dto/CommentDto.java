package com.hotmail.dolzhik.zalup_ca.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    @NotNull(message = "Post id cannot be empty.")
    private Integer postId;
    @NotNull(message = "Text cannot be empty.")
    @Size(min = 10, max = 256, message = "Text must not be bigger than 256 characters.")
    private String text;
}
