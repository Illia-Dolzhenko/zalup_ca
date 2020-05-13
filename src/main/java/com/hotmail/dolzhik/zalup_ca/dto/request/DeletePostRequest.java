package com.hotmail.dolzhik.zalup_ca.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeletePostRequest {
    @NotNull(message = "Post id cannot be empty")
    private int postId;
}
